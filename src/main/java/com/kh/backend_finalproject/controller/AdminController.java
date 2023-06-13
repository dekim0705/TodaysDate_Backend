package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.service.AdminService;
import com.kh.backend_finalproject.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // 💗 전체 문의 내역 조회 (최근순 정렬)
    @GetMapping("/inquiry")
    public ResponseEntity<List<ChatbotUserDto>> getAllInquiries() {
        List<ChatbotUserDto> inquiryList = adminService.findAllInquiryList();
        return new ResponseEntity<>(inquiryList,HttpStatus.OK);
    }
}
