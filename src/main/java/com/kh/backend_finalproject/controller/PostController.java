package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.UserRepository;
import com.kh.backend_finalproject.service.AuthService;
import com.kh.backend_finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthService authService;


    // 🔐게시글 작성 (SecurityContext 적용 OK)
    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostPinDto postPinDto,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        HttpServletRequest request) {

        boolean isCreate = postService.createPostWithPinAndPush(postPinDto, request, userDetails);
        if (isCreate) return new ResponseEntity<>("글 작성 성공❤️", HttpStatus.OK);
        else return new ResponseEntity<>("글 작성 실패💥", HttpStatus.NO_CONTENT);
    }

    // 🔐게시글 조회 (SecurityContext 적용 OK)
    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails,
                                           HttpServletRequest request) throws IllegalAccessException {
        PostDto post = postService.findPost(postId, request, userDetails);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // 🔐게시글 수정 (SecurityContext 적용 OK)
    @PutMapping(value = "/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostPinDto postPinDto,
                                        @AuthenticationPrincipal UserDetails userDetails,
                                        HttpServletRequest request) throws IllegalAccessException {
        try {
            boolean isUpdate = postService.updatePost(postId, postPinDto, request, userDetails);
            return new ResponseEntity<>("게시글 수정 성공 ❤️", HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("게시글 수정 실패 🚨️" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐게시글 삭제 (SecurityContext 적용 OK)
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails,
                                        HttpServletRequest request) throws IllegalAccessException {
        try {
            postService.deletePost(postId, request, userDetails);
            return new ResponseEntity<>("게시글 삭제 성공 ❤️", HttpStatus.ACCEPTED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("게시글 삭제 실패 🚨" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐댓글 작성 (SecurityContext 적용 OK)
    @PostMapping("/{postId}/reply")
    public ResponseEntity<?> createReply(@PathVariable Long postId, @RequestBody ReplyUserDto replyUserDto,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         HttpServletRequest request) throws IllegalAccessException {
        boolean isCreate = postService.createReply(postId, replyUserDto, request, userDetails);
        if (isCreate) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>("댓글 작성 실패!", HttpStatus.BAD_REQUEST);
    }

    // 🔐특정 사용자가 차단한 사용자의 댓글 제외 후 조회 (SecurityContext 적용 OK)
    @GetMapping("/{postId}/reply")
    public ResponseEntity<List<ReplyUserDto>> getReply(@PathVariable Long postId,
                                                       @AuthenticationPrincipal UserDetails userDetails,
                                                       HttpServletRequest request) throws IllegalAccessException {
        List<ReplyUserDto> replyUserDtos = postService.findReply(postId, request, userDetails);
        return new ResponseEntity<>(replyUserDtos, HttpStatus.OK);
    }

    // 🔐댓글 수정 (SecurityContext 적용 OK)
    @PutMapping("/{replyId}/reply")
    public ResponseEntity<?> updateReply(@PathVariable Long replyId, @RequestBody ReplyUserDto replyUserDto,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         HttpServletRequest request) {
        try {
            boolean isUpdate = postService.updateReply(replyId, replyUserDto, request, userDetails);
            return new ResponseEntity<>("댓글 수정 성공! ❤️", HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("댓글 수정 실패 🚨" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐댓글 삭제 (SecurityContext 적용 OK)
    @DeleteMapping("/{replyId}/reply")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId,
                                         @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        postService.deleteReply(replyId, request, userDetails);
        return new ResponseEntity<>("댓글 삭제 성공 ❤️", HttpStatus.ACCEPTED);
    }
}