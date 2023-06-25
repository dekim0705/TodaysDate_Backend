package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import com.kh.backend_finalproject.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/bookmark")
public class BookmarkController {
    private final BookmarkService bookmarkService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @GetMapping("/{postId}")
    public ResponseEntity<Map<String, Object>> isPostBookmarkedByUser(@PathVariable Long postId, HttpServletRequest request,
                                                      @AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> postBookmark = bookmarkService.isPostBookmarkedByUser(postId, request, userDetails);
        return new ResponseEntity<>(postBookmark, HttpStatus.OK);
    }
}
