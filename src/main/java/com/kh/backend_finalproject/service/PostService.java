package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.dto.UserDto;
import com.kh.backend_finalproject.entitiy.PinTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.PushTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.PinRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Autowired
    private SseService sseService;

    // ⚠️게시글 작성 (⭐️Spring Security 구현 후에 테스트 해볼 것!!)
    public PostTb createPostWithPinAndPush(Long userId, PostPinDto postPinDto) {
        // 1. 사용자 정보 가져오기(Spring Security...)
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserTb user = (UserTb) authentication.getPrincipal();
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
}
