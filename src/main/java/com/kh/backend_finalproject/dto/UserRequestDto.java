package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.*;
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
    private IsPush isPush;

    public UserTb toUserTb(PasswordEncoder passwordEncoder) {
        return UserTb.builder()
                .email(email)
                .pwd(passwordEncoder.encode(pwd))
                .nickname(nickname)
                .userRegion(userRegion)
                .isPush(isPush)
                .isMembership(IsMembership.FREE)
                .authority(Authority.ROLE_USER)
                .authKey(authKey)
                .isActive(IsActive.INACTIVE)
                .pfImg("https://firebasestorage.googleapis.com/v0/b/todaysdate-final-project.appspot.com/o/profile%2Fdefaultprofile.jpg?alt=media")
                .userComment("안녕하세요! 데이트 코스를 찾고 있어요!")
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, pwd);
    }
}
