package com.kh.backend_finalproject.dto.kakao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class KakaoReadyResponseDto {
    private String tid; // 결제 고유 번호
    private String next_redirect_mobile_url; // 모바일일 경우 받는 결제 페이지 URL
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지 URL
    private String created_at; // 결제 준비 요청 시간
}
