package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kakao")
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;

    @PostMapping("")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoAuth) {
        String token = kakaoService.requestKakaoToken(kakaoAuth);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestBody String token) {
        String userInfo = kakaoService.requestKakaoUserInfo(token);
        return ResponseEntity.ok(userInfo);
    }
}
