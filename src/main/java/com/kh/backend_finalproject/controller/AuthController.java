package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.dto.token.NewAccessTokenRequestDto;
import com.kh.backend_finalproject.dto.token.NewAccessTokenResponseDto;
import com.kh.backend_finalproject.dto.token.TokenDto;
import com.kh.backend_finalproject.service.AuthService;
import com.kh.backend_finalproject.service.EmailService;
import com.kh.backend_finalproject.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final TokenService tokenService;
    private final EmailService emailService;

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

    @GetMapping("/password/{email}")
    public ResponseEntity<?> sendMailWithPwdAuthKey(@PathVariable String email) throws Exception {
        authService.updatePasswordWithAuthKey(email);
        return new ResponseEntity<>("임시 비밀번호 발송 및 업데이트 완료 ❤️", HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Boolean> isValidEmail(@PathVariable String email) throws Exception {
        boolean isValid = authService.isValidEmail(email);
        return new ResponseEntity<>(isValid, HttpStatus.OK);
    }
}
