package com.kh.backend_finalproject.controller.kakao;

import com.kh.backend_finalproject.dto.kakao.KakaoApproveResponseDto;
import com.kh.backend_finalproject.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;

    // 결제 요청
    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay(@AuthenticationPrincipal UserDetails userDetails,
                                             HttpServletRequest request) {
        return new ResponseEntity<>(kakaoPayService.kakaoPayReady(request, userDetails), HttpStatus.OK);
    }

    // 결제 성공
    @GetMapping("/success")
    public ResponseEntity<?> afterPayRequest(@RequestParam("pg_token") String pgToken,
                                             @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        KakaoApproveResponseDto kakaoApproveResponse = kakaoPayService.approveResponse(pgToken, request, userDetails);
        return new ResponseEntity<>(kakaoApproveResponse, HttpStatus.OK);
    }

    // 결제 진행 중 취소
    @GetMapping("/cancel")
    public void cancel() {
        throw new IllegalArgumentException("결제 진행 중 취소되었습니다.");
    }

    // 결제 실패
    @GetMapping("/fail")
    public void fail() {
        throw new IllegalArgumentException("결제 실패하였습니다.");
    }
}
