package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {
    @Autowired
    UserService userService;

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    @PostMapping(value = "/profile")
    public ResponseEntity<List<UserProfileDto>> getUserProfileBar2(@RequestBody String email) {
        List<UserProfileDto> profileDtos = userService.getUserProfileInfo(email);
        if (profileDtos.isEmpty()) {
            return new ResponseEntity<>(profileDtos, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(profileDtos, HttpStatus.OK);
        }
    }

    // 마이페이지 - 회원 전체 게시글 가져오기 (글번호, 제목, 본문, 닉네임, 작성일, 조회수)
//    @GetMapping(value = "/posts/{userNum}")
//    public ResponseEntity<List<PostDto>> getPosts(@RequestParam Long userNum) {
//        List<PostDto> userPosts = userService.getUserPosts(userNum);
//        return new ResponseEntity<>(userPosts, HttpStatus.OK);
//    }

}
