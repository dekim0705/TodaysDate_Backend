package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.service.EmailService;
import com.kh.backend_finalproject.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/join")
public class JoinController {
    @Autowired
    UserService userService;
    @Autowired
    EmailService emailService;

    // ✅ 회원가입 - 닉네임 중복 확인
    @PostMapping("/dupnickname")
    public ResponseEntity<Boolean> checkDuplicateNickname(@RequestParam String nickname) {
        Optional<UserTb> user = userService.findUserByNickname(nickname);

        if (user.isPresent()) {
            System.out.println("🍒 존재하는 닉네임 (가입 불가): " + nickname);
            return ResponseEntity.ok(false);
        } else {
            System.out.println("🍒 존재하지 않는 닉네임 (가입 가능): " + nickname);
            return ResponseEntity.ok(true);
        }
    }

    // ✅ 회원가입 - 이메일 중복 확인
    @PostMapping("/dupemail")
    public ResponseEntity<Boolean> checkDuplicateEmail(@RequestParam String email) {
        boolean isDuplicate = userService.findUserByEmail(email);
        if(isDuplicate) {
            System.out.println("🍒 존재하는 이메일 (가입 불가): " + email);
            return ResponseEntity.ok(false);
        } else {
            System.out.println("🍒 존재하지 않는 이메일 (가입 가능): " + email);
            return ResponseEntity.ok(true);
        }
        // if(isDuplicate) return ResponseEntity.ok(false);
        // else return ResponseEntity.ok(true);
    }

    // ✅ 회원가입 - 이메일 인증 (인증키 생성 + 메일 발송)
    @PostMapping("/email-auth")
    public ResponseEntity<Boolean> sendMailWithAuthKey(@RequestParam("email") String email) throws Exception {
        String authKey = emailService.sendSimpleMessage(email);
        System.out.println("🍒 인증 키 : " + authKey);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    // ✅ 회원가입 - 이메일 인증 (인증키 확인)
    @PostMapping("/auth")
    public ResponseEntity<Boolean> checkMailWithAuthKey(@RequestParam("email") String email, @RequestParam("authKey") String authKey) throws Exception {
        try {
            userService.checkEmailWithAuthKey(email, authKey);
            System.out.println("🍒 이메일 인증 완료 : " + email);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            System.out.println("🍒 이메일 인증 실패 : " + e.getMessage());
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

}
