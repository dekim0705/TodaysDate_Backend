package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.dto.kakao.KakaoApproveResponseDto;
import com.kh.backend_finalproject.dto.kakao.KakaoReadyResponseDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
    private static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    private KakaoReadyResponseDto kakaoReadyResponseDto;
    private final TokenProvider tokenProvider;
    private final AuthService authService;
    private final UserRepository userRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.admin-key}")
    private String adminKey;

    // 🔐'결제 준비하기' 하기 위한 카카오페이 요청 양식 (SecurityContext 적용 OK)
    public KakaoReadyResponseDto kakaoPayReady(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        MultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "가맹점 주문 번호");
        parameters.add("partner_user_id", "가맹점 회원 ID");
        parameters.add("item_name", "멤버십 회원권");
        parameters.add("quantity", 1);
        parameters.add("total_amount", 1990);
        parameters.add("tax_free_amount", 0);
        parameters.add("approval_url", "http://localhost:3000/kakao/auth/callback");
        parameters.add("cancel_url", "http://localhost:3000/membership/cancel");
        parameters.add("fail_url", "http://localhost:3000/membership/fail");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();

        kakaoReadyResponseDto = restTemplate.postForObject("https://kapi.kakao.com/v1/payment/ready",
                requestEntity, KakaoReadyResponseDto.class);

        return kakaoReadyResponseDto;
    }

    // 🔐결제 완료 승인 (SecurityContext 적용 OK)
    public KakaoApproveResponseDto approveResponse(String pgToken, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

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

        if(approveResponse != null) {
            user.setIsMembership(IsMembership.MEMBERSHIP);
            userRepository.save(user);
        }

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
