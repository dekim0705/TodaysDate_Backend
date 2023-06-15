package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.PinDto;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.*;
import com.kh.backend_finalproject.repository.PinRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReplyRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Autowired
    private SseService sseService;

    // ⚠️게시글 작성 (⭐️Spring Security 구현 후에 테스트 해볼 것!!)
    public boolean createPostWithPinAndPush(Long userId, PostPinDto postPinDto) {
        // 1. 사용자 정보 가져오기(추후 Spring Security...)
        Optional<UserTb> user = userRepository.findById(userId);

        // 2. 게시글 저장
        PostTb post = postPinDto.getPost();
        post.setUser(user.get());
        PostTb savePost = postRepository.save(post);

        // 3. pin(경로) 저장
        for (PinTb pin : postPinDto.getPins()) {
            pin.setPost(savePost);
            pinRepository.save(pin);
        }

        // 4. 관심 지역 설정한 사용자들에게 알림
        List<UserTb> subscribedUsers = userRepository.findByUserRegion(savePost.getRegion());
        for (UserTb subscribedUser : subscribedUsers) {
            if (subscribedUser.getIsPush() == IsPush.PUSH) {
                PushTb pushTb = new PushTb();
                pushTb.setUser(subscribedUser);
                pushTb.setPost(savePost);
                pushTb.setSendDate(LocalDateTime.now());
                sseService.sendEvent(pushTb);
            }
        }
        return true;
    }

    // ✅게시글 조회
    public PostDto findPost(Long postId) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));
        int bookmarkCount = postRepository.findBookmarkCountByPostId(postId);
        PostDto postDto = new PostDto();
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

    // ✅게시글 수정
    public boolean updatePost(Long postId, PostPinDto postPinDto) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));

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
    }

    // ✅게시글 삭제
    public void deletePost(Long postId) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        postRepository.delete(post);
    }

    // ✅댓글 작성
    public boolean createReply(Long postId, ReplyUserDto replyUserDto) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        UserTb user = userRepository.findById(replyUserDto.getUserNum())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

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

    // ✅댓글 조회
    public List<ReplyUserDto> findReply(Long postId) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));
        List<ReplyUserDto> replyUserDtos = post.getReplies().stream()
                .map(reply -> new ReplyUserDto(reply.getUser().getNickname(), reply.getContent(), reply.getWriteDate(), reply.getUser().getPfImg()))
                .collect(Collectors.toList());

        return replyUserDtos;
    }

    // ✅댓글 수정
    public boolean updateReply(Long replyId, ReplyUserDto replyUserDto) throws IllegalAccessException {
        ReplyTb reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalAccessException("해당 댓글이 없습니다." + replyId));

        reply.setContent(replyUserDto.getContent());
        replyRepository.save(reply);

        return true;
    }

    // ✅댓글 삭제
    public void deleteReply(Long replyId) {
        ReplyTb reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        replyRepository.delete(reply);
    }
}