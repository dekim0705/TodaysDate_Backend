package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/chatbot/")
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping("inquiry")
    public ResponseEntity<String> createInquiry(@RequestBody ChatbotDto chatbotDto, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        try {
            chatbotService.createInquiry(chatbotDto, userDetails, request);
            return ResponseEntity.ok("챗봇 문의 작성 성공!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("챗봇 문의 작성 실패..");
        }
    }
}


