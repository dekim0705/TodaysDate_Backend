package com.kh.backend_finalproject.controller.kakao;

import com.kh.backend_finalproject.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {
    private final KakaoPayService kakaoPayService;

    // 결제 요청
    @PostMapping("/ready")
    public ResponseEntity<?> readyToKakaoPay() {
        return new ResponseEntity<>(kakaoPayService.kakaoPayReady(), HttpStatus.OK);
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
