package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.UserDto;
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

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개)
    @PostMapping(value = "/profile")
    public ResponseEntity<List<UserDto>> getUserProfileBar(@RequestBody String email) {
        List<UserDto> userDtos = userService.findUserInfo(email);
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
