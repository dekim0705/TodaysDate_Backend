package com.kh.backend_finalproject.dto.token;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewAccessTokenResponseDto {
    private String accessToken;

    public NewAccessTokenResponseDto(String newAccessToken) {
        accessToken = newAccessToken;
    }
}
