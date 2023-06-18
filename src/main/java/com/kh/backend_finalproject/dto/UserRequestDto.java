package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.Authority;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.UserTb;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {
    private String email;
    private String pwd;
    private String nickname;
    private RegionStatus userRegion;
    private String authKey;

    public UserTb toUserTb(PasswordEncoder passwordEncoder) {
        return UserTb.builder()
                .email(email)
                .pwd(passwordEncoder.encode(pwd))
                .nickname(nickname)
                .userRegion(userRegion)
                .isPush(IsPush.PUSH)
                .isMembership(IsMembership.FREE)
                .authority(Authority.ROLE_USER)
                .authKey(authKey)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, pwd);
    }
}
