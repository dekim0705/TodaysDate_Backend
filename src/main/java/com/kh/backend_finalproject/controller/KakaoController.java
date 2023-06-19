package com.kh.backend_finalproject.controller;

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
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    // 🔑카카오에게 인가 코드 전달하여 토큰 요청(Access, Refresh)
    @PostMapping("")
    public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoAuth) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("code", kakaoAuth.get("authorizationCode"));

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                tokenUri,
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );
        System.out.println("🔑" + response.getBody());
        return ResponseEntity.ok(response.getBody());
    }

    // 🔑받은 토큰을 카카오에게 전달하여 사용자 정보 요청
    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    @PostMapping("/userinfo")
    public ResponseEntity<?> getUserInfo(@RequestBody String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> response = restTemplate.exchange(
                userInfoUri,
                HttpMethod.GET,
                entity,
                String.class
        );
        System.out.println("😊 : " + response.getBody());
        return ResponseEntity.ok(response.getBody());
    }
}
