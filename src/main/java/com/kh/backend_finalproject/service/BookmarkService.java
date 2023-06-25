package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.entitiy.BookmarkTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.BookmarkRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public Map<String, Object> isPostBookmarkedByUser(Long postId, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        Optional<BookmarkTb> bookmarkTbOptional = bookmarkRepository.findByPostAndUser(post, user);
        boolean isBookmarked = bookmarkTbOptional.isPresent();
        String folderName = isBookmarked ? bookmarkTbOptional.get().getFolder().getName() : null;

        Map<String, Object> postBookmark = new HashMap<>();
        postBookmark.put("folderName", folderName);
        postBookmark.put("isBookmarked", isBookmarked);

        return postBookmark;
    }
}
