package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.dto.PostPinDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {
    @Autowired
    PostService postService;

    // ⭐️글 작성 Controller는 사용자 정보 받아야 해서 로그인 구현 후에 마무리 !!!
    @PostMapping("/posts")
    public ResponseEntity<PostTb> createPost(@RequestBody PostPinDto postPinDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserTb user = (UserTb) authentication.getPrincipal();
        PostTb post = postService.createPostWithPinAndPush(postPinDto);
        if(post != null) return new ResponseEntity<>(post, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
