package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.Authority;
import com.kh.backend_finalproject.constant.IsActive;
import com.kh.backend_finalproject.dto.token.TokenDto;
import com.kh.backend_finalproject.dto.UserRequestDto;
import com.kh.backend_finalproject.dto.UserResponseDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.UserRepository;
import com.kh.backend_finalproject.utils.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    @Autowired
    EmailService emailService;

    @Autowired
    JavaMailSender javaMailSender;

    // 🔐회원가입
    public UserResponseDto join(UserRequestDto requestDto) throws Exception {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RuntimeException("이미 가입되어 있는 사용자 압니다. 🐿️");
        }

        String authKey = emailService.createKey();
        requestDto.setAuthKey(authKey);

        // 🚧 이메일에 인증 링크 포함하여 전송
        String emailContent = "안녕하세요! <br /><br />오늘의 데이트 회원가입을 완료하기 위해 아래 링크를 클릭해 주세요. <br /><br />";
        emailContent += "<a href=\"http://localhost:8111/join/auth?email=" + requestDto.getEmail() + "&authKey=" + requestDto.getAuthKey() + "\">인증하기</a>";
        emailService.sendEmailWithLink(requestDto.getEmail(), "[오늘의 데이트] 회원가입 이메일 인증", emailContent);

        UserTb user = requestDto.toUserTb(passwordEncoder);
        return UserResponseDto.of(userRepository.save(user));
    }

    // 🔐로그인
    /* ✨예외 처리 ✨
        1. 사용자가 있는지 확인✅
        2. 아이디 비밀번호 맞는지 확인✅
        3. 이메일 인증 관련 IsActive 유무 확인✅
    */
    public TokenDto login(UserRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();
        // 사용자가 있는지 확인
        UserTb loginUser = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. 회원가입 진행 후 다시 시도해주세요."));

        // 비밀번호 맞는지 확인
        if (!passwordEncoder.matches(requestDto.getPwd(), loginUser.getPwd())) {
            throw new IllegalArgumentException("비밀번호가 맞지 않습니다.");
        }

        // 권한 확인
        if (loginUser.getAuthority().equals(Authority.ROLE_ADMIN)) {
            try {
                Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
                return tokenProvider.generateTokenDto(authentication);
            } catch (AuthenticationException e) {
                throw e;
            }
        } else if (loginUser.getAuthority().equals(Authority.ROLE_USER)) {
            if (loginUser.getIsActive().equals(IsActive.ACTIVE)) {
                try {
                    Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);
                    return tokenProvider.generateTokenDto(authentication);
                } catch (AuthenticationException e) {
                    throw e;
                }
            } else {
                throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
            }
        } else {
            throw new IllegalArgumentException("권한이 올바르지 않습니다.");
        }
    }

    // 🔑토큰 검증 및 사용자 정보 추출
    public UserTb validateTokenAndGetUser(HttpServletRequest request, UserDetails userDetails) {
        // ♻️토큰 추출
        String accessToken = request.getHeader("Authorization");
        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            accessToken = accessToken.substring(7);
        }
        // 🔑토큰 유효한지 검증
        if (accessToken != null && tokenProvider.validateToken(accessToken)) {
            Long userId = Long.valueOf(userDetails.getUsername());
            UserTb user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));
            return user;
        } else {
            throw new TokenExpiredException("🔴토큰이 만료됐습니다. Refresh Token을 보내주세요.");
        }
    }

    // 임시 비밀번호 발송 및 회원정보 업데이트
    public void updatePasswordWithAuthKey(String to) throws Exception {
        String tempPw = emailService.sendPasswordAuthKey(to);
        UserTb user = userRepository.findByEmail(to)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        // 임시 비밀번호 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(tempPw);
        user.setPwd(encodedPassword);
        userRepository.save(user);
    }

    // 이메일 유효한지 확인
    public boolean isValidEmail(String email) throws Exception {
        UserTb user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        boolean isValid = user.getEmail().isEmpty() ? false : true;

        return isValid;
    }
}
