package com.kh.backend_finalproject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccessTokenRequestDto {
    private String refreshToken;
}
