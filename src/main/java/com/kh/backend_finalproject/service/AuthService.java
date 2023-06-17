package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.dto.TokenDto;
import com.kh.backend_finalproject.dto.UserRequestDto;
import com.kh.backend_finalproject.dto.UserResponseDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserResponseDto join(UserRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 사용자 압니다. 🐿️");
        }

        UserTb user = requestDto.toUserTb(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    public TokenDto login(UserRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        try {
            Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
            return tokenProvider.generateTokenDto(authentication);
        } catch (AuthenticationException e) {
            System.out.println("뭔가 잘못됐다....⛑️" + e.getMessage());
            throw e;
        }
    }
}
