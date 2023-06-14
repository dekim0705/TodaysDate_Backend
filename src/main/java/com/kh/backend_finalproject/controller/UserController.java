package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.UserDto;
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
    public ResponseEntity<List<UserProfileDto>> getUserProfileBar(@RequestParam String email) {
        List<UserProfileDto> profileDtos = userService.getUserProfileInfo(email);
            return new ResponseEntity<>(profileDtos, HttpStatus.OK);
    }
    // ✅ 마이페이지 - 회원의 모든 게시글 가져오기
    @GetMapping(value = "/posts")
    public ResponseEntity<List<UserDto>> getAllPosts(@RequestParam("email") String email) {
        List<UserDto> posts = userService.getAllUserPosts(email);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    // ✅ 마이페이지 - 회원의 모든 댓글 가져오기
    @GetMapping(value = "/replies")
    public ResponseEntity<List<UserDto>> getAllReplies(@RequestParam("email") String email) {
        List<UserDto> replies = userService.getAllUserReplies(email);
        return new ResponseEntity<>(replies,HttpStatus.OK);
    }

    @GetMapping("/{email}/notification-status")
    public ResponseEntity<IsPush> getUserNotificationStatus(@PathVariable("email") String email) {
        IsPush notificationStatus = userService.getUserNotificationStatus(email); {
            return ResponseEntity.ok(notificationStatus);
        }
    }


}
