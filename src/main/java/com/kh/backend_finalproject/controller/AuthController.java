package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.service.AuthService;
import com.kh.backend_finalproject.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;

    @PostMapping("/join")
    public ResponseEntity<UserResponseDto> join(@RequestBody UserRequestDto requestDto) throws Exception {
        return ResponseEntity.ok(authService.join(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/token")
    public ResponseEntity<NewAccessTokenResponseDto> createNewAccessToken(@RequestBody NewAccessTokenRequestDto requestDto) {
        String newAccessToken = tokenService.createNewAccessToken(requestDto.getRefreshToken());
        NewAccessTokenResponseDto responseDto = new NewAccessTokenResponseDto(newAccessToken);
        return ResponseEntity.ok(responseDto);
    }

}
