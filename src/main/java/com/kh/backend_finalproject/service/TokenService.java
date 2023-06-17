package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.RefreshTokenRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TokenService {
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public String createNewAccessToken(String refreshToken) {
        if(!tokenProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("🚨");
        }

        Long userId = refreshTokenRepository.findByRefreshToken(refreshToken).get().getUserId();
        UserTb user = userRepository.findById(userId).get();
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().toString()));
        // user를 Authentication 객체로 변환해야 함... 음..!!
        UserDetails userDetails = User.withUsername(user.getEmail())
                .password(user.getPwd())
                .authorities(authorities)
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", authorities);

        return tokenProvider.generateTokenDto(authentication).getAccessToken();
    }
}
