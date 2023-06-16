package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.entitiy.UserTb;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private String email;
    private String nickname;

    public static UserResponseDto of(UserTb userTb) {
        return UserResponseDto.builder()
                .email(userTb.getEmail())
                .nickname(userTb.getNickname())
                .build();
    }
}
