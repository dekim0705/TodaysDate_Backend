package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.IsPush;
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
import java.util.List;
import java.util.Optional;

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
    public PostTb createPostWithPinAndPush(Long userId, PostPinDto postPinDto) {
        // 1. 사용자 정보 가져오기(추후 Spring Security...)
        Optional<UserTb> user = userRepository.findById(userId);

        // 2. 게시글 저장
        PostTb post = postPinDto.getPost();
        post.setUser(user.get());
        PostTb savePost = postRepository.save(post);

        // 3. pin(경로) 저장
        for(PinTb pin : postPinDto.getPins()) {
            pin.setPost(savePost);
            pinRepository.save(pin);
        }

        // 4. 관심 지역 설정한 사용자들에게 알림
        List<UserTb> subscribedUsers = userRepository.findByUserRegion(savePost.getRegion());
        for(UserTb subscribedUser : subscribedUsers) {
            if (subscribedUser.getIsPush() == IsPush.PUSH) {
                PushTb pushTb = new PushTb();
                pushTb.setUser(subscribedUser);
                pushTb.setPost(savePost);
                pushTb.setSendDate(LocalDateTime.now());

                sseService.sendEvent(pushTb);
            }
        }
        return savePost;
    }
    // ✅게시글 조회
    public PostTb findPost(Long postId) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));
        return post;
    }
    // ✅게시글 수정
    public PostTb updatePost(Long postId, PostTb updatePostData) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalAccessException("해당 게시글이 없습니다." + postId));
        post.setTitle(updatePostData.getTitle());
        post.setRegion(updatePostData.getRegion());
        post.setCourse(updatePostData.getCourse());
        post.setTheme(updatePostData.getTheme());
        post.setDistrict(updatePostData.getDistrict());
        post.setComment(updatePostData.getComment());
        post.setPlaceTag(updatePostData.getPlaceTag());
        post.setImgUrl(updatePostData.getImgUrl());
        post.setContent(updatePostData.getContent());

        return postRepository.save(post);
    }
    // ✅게시글 삭제
    public void deletePost(Long postId) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        postRepository.delete(post);
    }
    // 댓글 작성
    public ReplyTb createReply(Long postId, Long userId, ReplyUserDto replyUserDto) throws IllegalAccessException {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        UserTb user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        ReplyTb reply = new ReplyTb();
        reply.setContent(replyUserDto.getContent());
        reply.setWriteDate(LocalDateTime.now());
        reply.setPost(post);
        reply.setUser(user);

        return replyRepository.save(reply);
    }
    // 댓글 조회
//    public List<ReplyTb> findReply(Long PostId) {
//
//    }
    // 댓글 수정

    // 댓글 삭제
}
