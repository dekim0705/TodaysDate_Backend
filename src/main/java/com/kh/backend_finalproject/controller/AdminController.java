package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.AdDto;
import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.service.AdminService;
import com.kh.backend_finalproject.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // 💗 전체 댓글 내역 조회 (문의일 최근순 정렬)
    @GetMapping("/reply")
    public ResponseEntity<List<ReplyUserDto>> getAllReplies() {
        List<ReplyUserDto> replyList = adminService.findAllReplyList();
        return new ResponseEntity<>(replyList,HttpStatus.OK);
    }

    // 💗 광고 추가
    @PostMapping("/ad/new")
    public ResponseEntity<AdDto> addAd(@RequestBody AdDto adDto) {
        AdDto savedAdDto = adminService.createAd(adDto);
        return new ResponseEntity<>(savedAdDto, HttpStatus.OK);
    }
    // 💗 광고 조회
    @GetMapping("/ad")
    public ResponseEntity<List<AdDto>> getAllAds() {
        List<AdDto> adList = adminService.findAllAdList();
        return new ResponseEntity<>(adList,HttpStatus.OK);
    }

}


