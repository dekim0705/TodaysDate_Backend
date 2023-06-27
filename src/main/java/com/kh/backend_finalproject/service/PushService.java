package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.dto.PushDto;
import com.kh.backend_finalproject.entitiy.PushTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.PushRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PushService {
    private final UserRepository userRepository;
    private final PushRepository pushRepository;
    private final AuthService authService;

    // 🔐해당 사용자의 모든 알림 목록 가져오기 (SecurityContext 적용 OK)
    public List<PushDto> fetchAllPushList(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        List<PushTb> pushList = pushRepository.findByUserOrderBySendDateDesc(user);

        List<PushDto> pushDtoList = pushList.stream().map(pushTb -> {
            PushDto pushDto = new PushDto();
            pushDto.setPushId(pushTb.getId());
            pushDto.setSendDate(pushTb.getSendDate());
            pushDto.setUserId(pushTb.getUser().getId());
            pushDto.setPostId(pushTb.getPost().getId());
            return pushDto;
        }).collect(Collectors.toList());

        return pushDtoList;
    }
}
