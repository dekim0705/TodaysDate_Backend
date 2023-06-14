package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    @Autowired
    PostService postService;

    // ⚠️️게시글 작성 Controller는 사용자 정보 받아야 해서 로그인 구현 후에 마무리 !!!
    @PostMapping("")
    public ResponseEntity<Boolean> createPost(@RequestBody PostPinDto postPinDto) {
        boolean isCreate = postService.createPostWithPinAndPush(postPinDto.getUserId(), postPinDto);
        if(isCreate) return new ResponseEntity<>(isCreate, HttpStatus.OK);
        else return new ResponseEntity<>(isCreate, HttpStatus.NO_CONTENT);
    }
    // ✅게시글 조회
    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long postId) throws IllegalAccessException {
        PostDto post = postService.findPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    // ✅게시글 수정
    @PutMapping(value = "/{postId}")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostTb updatePostData) throws IllegalAccessException {
        try {
            PostDto post = postService.updatePost(postId, updatePostData);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("게시글 수정 실패..⚠️" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    // ✅게시글 삭제
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) throws IllegalAccessException {
        try {
            postService.deletePost(postId);
            return new ResponseEntity<>("게시글 삭제 성공 ❤️", HttpStatus.ACCEPTED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("게시글 삭제 실패 🚨" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
