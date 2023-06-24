package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.ChatbotRepository;

import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;


@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatbotService {
    private final ChatbotRepository chatbotRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    // 💗챗봇 문의 작성
    public void createInquiry(ChatbotDto chatbotDto, UserDetails userDetails, HttpServletRequest request) {
        UserTb authUser = authService.validateTokenAndGetUser(request, userDetails);
        ChatbotTb inquiry = new ChatbotTb();
        inquiry.setInquiryContent(chatbotDto.getInquiryContent());
        inquiry.setInquiryEmail(chatbotDto.getInquiryEmail());
        inquiry.setInquiryDate(LocalDateTime.now());
        inquiry.setInquiryStatus("대기");
        inquiry.setUser(authUser);
        chatbotRepository.save(inquiry);
    }
}

