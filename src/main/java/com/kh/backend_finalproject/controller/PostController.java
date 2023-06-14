package com.kh.backend_finalproject.controller;
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
    @PostMapping("/")
    public ResponseEntity<PostTb> createPost(@RequestBody Long userId, PostPinDto postPinDto) {
        PostTb post = postService.createPostWithPinAndPush(userId, postPinDto);
        if(post != null) return new ResponseEntity<>(post, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // ✅게시글 조회
    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostTb> getPost(@PathVariable Long postId) throws IllegalAccessException {
        PostTb post = postService.findPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    // ✅게시글 수정
    @PutMapping(value = "/{postId}")
    public ResponseEntity<PostTb> updatePost(@PathVariable Long postId, @RequestBody PostTb updatePostData) throws IllegalAccessException {
        PostTb post = postService.updatePost(postId, updatePostData);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
    // ✅게시글 삭제
    @DeleteMapping(value = "/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) throws IllegalAccessException {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
