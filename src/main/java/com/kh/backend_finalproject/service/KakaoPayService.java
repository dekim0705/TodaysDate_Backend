package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.dto.kakao.KakaoApproveResponseDto;
import com.kh.backend_finalproject.dto.kakao.KakaoReadyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
    private static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    private KakaoReadyResponseDto kakaoReadyResponseDto;

    @Value("${spring.security.oauth2.client.registration.kakao.admin-key}")
    private String adminKey;

    // '결제 준비하기' 하기 위한 카카오페이 요청 양식
    public KakaoReadyResponseDto kakaoPayReady() {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("item_name", "멤버십 회원권");
        parameters.add("quantity", 1);
        parameters.add("total_amount", 1990);
        parameters.add("tax_free_amount", 0);
        parameters.add("approval_url", "http://localhost:3000/kakao/auth/callback"); // 추후 바꿔야 함 ✨
        parameters.add("cancel_url", "http://localhost:3000/kakao/auth/callback"); // 추후 바꿔야 함 ✨
        parameters.add("fail_url", "http://localhost:3000/kakao/auth/callback"); // 추후 바꿔야 함 ✨

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        kakaoReadyResponseDto = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready",
                requestEntity, KakaoReadyResponseDto.class);

        return kakaoReadyResponseDto;
    }

    // 결제 완료 승인
    public KakaoApproveResponseDto approveResponse(String pgToken) {
        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReadyResponseDto.getTid());
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponseDto approveResponse = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/approve",
                requestEntity, KakaoApproveResponseDto.class);

        return approveResponse;
    }

    // 카카오에서 요구하는 헤더 값
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        String auth = "KakaoAK " + adminKey;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }
}
