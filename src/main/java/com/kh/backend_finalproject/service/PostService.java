package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.PinDto;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.*;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.*;
import com.kh.backend_finalproject.utils.BlockFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;
    private final ReplyRepository replyRepository;
    private final PushRepository pushRepository;
    private final BlockRepository blockRepository;
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final SimpMessagingTemplate messagingTemplate;

    // 🔐게시글 작성 (SecurityContext 적용 OK)
    public boolean createPostWithPinAndPush(PostPinDto postPinDto,
                                            HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 게시글 저장
        postPinDto.setUserId(user.getId());
        PostTb post = postPinDto.getPost();
        post.setUser(user);
        PostTb savePost = postRepository.save(post);

        // 2. pin(경로) 저장
        for (PinTb pin : postPinDto.getPins()) {
            pin.setPost(savePost);
            pinRepository.save(pin);
        }

        // 3. 관심 지역 설정한 사용자들에게 알림
        List<UserTb> subscribedUsers = userRepository.findByUserRegion(savePost.getRegion());
        for (UserTb subscribedUser : subscribedUsers) {
            if (subscribedUser.getIsPush() == IsPush.PUSH) {
                PushTb pushTb = new PushTb();
                pushTb.setUser(subscribedUser);
                pushTb.setPost(savePost);
                pushTb.setSendDate(LocalDateTime.now());
                pushRepository.save(pushTb);

                // 💡사용자에게 푸시 알림 보내기
                log.info("🔴Sending push notification to /region/" + savePost.getRegion());
                messagingTemplate.convertAndSend("/region/" + savePost.getRegion() + "/" + subscribedUser.getId(),
                        "새 게시글이 등록되었습니다: " + savePost.getTitle());
            }
        }
        return true;
    }

    // 🔐게시글 조회 (SecurityContext 적용 OK)
    public PostDto findPost(Long postId, HttpServletRequest request, UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));

        // 조회수 증가
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        int bookmarkCount = postRepository.findBookmarkCountByPostId(postId);
        PostDto postDto = new PostDto();
        postDto.setPostId(post.getId());
        postDto.setUserId(post.getUser().getId());
        postDto.setPfImg(post.getUser().getPfImg());
        postDto.setNickname(post.getUser().getNickname());
        postDto.setTitle(post.getTitle());
        postDto.setDistrict(post.getDistrict());
        postDto.setBookmarkCount(bookmarkCount);
        postDto.setViewCount(post.getViewCount());
        postDto.setCourse(post.getCourse());
        postDto.setTheme(post.getTheme());
        postDto.setComment(post.getComment());
        List<PinDto> pinDtos = post.getPins().stream()
                .map(pin -> new PinDto(pin.getLatitude(), pin.getLongitude(), pin.getRouteNum()))
                .collect(Collectors.toList());
        postDto.setPins(pinDtos);
        postDto.setPlaceTag(post.getPlaceTag());
        postDto.setContent(post.getContent());
        postDto.setImgUrl(post.getImgUrl());
        postDto.setWriteDate(post.getWriteDate());
        return postDto;
    }

    // 🔐게시글 수정 (SecurityContext 적용 OK)
    public boolean updatePost(Long postId, PostPinDto postPinDto,
                              HttpServletRequest request, UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));

        if (user.getId().equals(post.getUser().getId())) {
            // 핀 초기화 후 다시 추가 ^^..
            pinRepository.deleteAllByPost(post);
            List<PinTb> newPins = postPinDto.getPins().stream()
                    .map(pinDto -> {
                        PinTb newPin = new PinTb();
                        newPin.setLatitude(pinDto.getLatitude());
                        newPin.setLongitude(pinDto.getLongitude());
                        newPin.setRouteNum(pinDto.getRouteNum());
                        newPin.setPost(post);
                        return pinRepository.save(newPin);
                    }).collect(Collectors.toList());

            post.setTitle(postPinDto.getPost().getTitle());
            post.setRegion(postPinDto.getPost().getRegion());
            post.setCourse(postPinDto.getPost().getCourse());
            post.setTheme(postPinDto.getPost().getTheme());
            post.setDistrict(postPinDto.getPost().getDistrict());
            post.setComment(postPinDto.getPost().getComment());
            post.setPlaceTag(postPinDto.getPost().getPlaceTag());
            post.setImgUrl(postPinDto.getPost().getImgUrl());
            post.setContent(postPinDto.getPost().getContent());
            postRepository.save(post);

            return true;
        } else {
            throw new IllegalArgumentException("요청한 자는 글 작성자가 아닙니다. 수정 할 수 없습니다.");
        }
    }

    // 🔐게시글 삭제 (SecurityContext 적용 OK)
    public void deletePost(Long postId, HttpServletRequest request,
                           UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);


        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if (user.getId().equals(post.getUser().getId())) {
            postRepository.delete(post);
        } else {
            throw new IllegalArgumentException("요청한 자는 글 작성자가 아닙니다. 삭제 할 수 없습니다.");
        }
    }

    // 🔐댓글 작성 (SecurityContext 적용 OK)
    public boolean createReply(Long postId, ReplyUserDto replyUserDto,
                               HttpServletRequest request, UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        ReplyTb reply = new ReplyTb();
        if (replyUserDto.getContent() == null || replyUserDto.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용이 비어 있습니다.");
        }
        reply.setContent(replyUserDto.getContent());
        reply.setWriteDate(LocalDateTime.now());
        reply.setPost(post);
        reply.setUser(user);

        ReplyTb savedReply = replyRepository.save(reply);

        return savedReply != null;
    }

    // 🌴🔐특정 사용자가 차단한 사용자의 댓글 제외 후 조회 (SecurityContext 적용 OK)
    public List<ReplyUserDto> findReply(Long postId, HttpServletRequest request, UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(user.getId(), blockRepository);

        // 2. 특정 게시글 전체 댓글 가져오기
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));

        List<ReplyUserDto> allReplies = post.getReplies().stream()
                .map(reply -> new ReplyUserDto(reply.getId(), reply.getUser().getNickname(), reply.getContent(),
                        reply.getWriteDate(), reply.getUser().getPfImg(), reply.getUser().getId(), reply.getUser().getUserComment()))
                .collect(Collectors.toList());

        // 3. 차단한 사용자가 작성한 댓글 제외
        for (ReplyUserDto reply : allReplies) {
            if (blockedUserIds.contains(reply.getUserNum())) {
                reply.setBlocked(true);
            } else {
                reply.setBlocked(false);
            }
        }
        return allReplies;
    }

    // 🔐댓글 수정 (SecurityContext 적용 OK)
    public boolean updateReply(Long replyId, ReplyUserDto replyUserDto,
                               HttpServletRequest request, UserDetails userDetails) throws IllegalAccessException {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        ReplyTb reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalAccessException("해당 댓글이 없습니다." + replyId));

        if (user.getId().equals(reply.getUser().getId())) {
            reply.setContent(replyUserDto.getContent());
            replyRepository.save(reply);

            return true;
        } else {
            throw new IllegalArgumentException("요청한 자는 댓글 작성자가 아닙니다. 수정할 수 없습니다.");
        }
    }

    // 🔐댓글 삭제 (SecurityContext 적용 OK)
    public void deleteReply(Long replyId, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        ReplyTb reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));

        if (user.getId().equals(reply.getUser().getId())) {
            replyRepository.delete(reply);
        } else {
            throw new IllegalArgumentException("요청한 자는 댓글 작성자가 아닙니다. 삭제할 수 없습니다.");
        }
    }
}