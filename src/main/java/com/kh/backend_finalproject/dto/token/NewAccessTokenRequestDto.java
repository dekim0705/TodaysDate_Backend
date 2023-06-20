package com.kh.backend_finalproject.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccessTokenRequestDto {
    private String refreshToken;
}
