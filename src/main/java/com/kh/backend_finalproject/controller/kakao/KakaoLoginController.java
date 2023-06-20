package com.kh.backend_finalproject.controller.kakao;

import com.kh.backend_finalproject.service.KakaoLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoLoginController {
    private final KakaoLoginService kakaoLoginService;

    @PostMapping("")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoAuth) {
        String token = kakaoLoginService.requestKakaoToken(kakaoAuth);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestBody String token) {
        String userInfo = kakaoLoginService.requestKakaoUserInfo(token);
        return ResponseEntity.ok(userInfo);
    }
}
