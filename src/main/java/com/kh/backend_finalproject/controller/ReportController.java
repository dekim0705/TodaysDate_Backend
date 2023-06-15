package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.ReportRequestDto;
import com.kh.backend_finalproject.service.PostService;
import com.kh.backend_finalproject.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    ReportService reportService;

    // ✅게시글 신고하기
    @DeleteMapping("/post/{postId}/report")
    public ResponseEntity<?> reportPost(@PathVariable Long postId) {
        reportService.reportPost(postId);
        return new ResponseEntity<>("게시글 신고 완료 ❤️", HttpStatus.ACCEPTED);
    }

    // ✅사용자 차단하기
    @PostMapping("/user/{blockerId}/block/{blockedId}")
    public ResponseEntity<?> blockUser(@PathVariable Long blockerId, @PathVariable Long blockedId) {
        reportService.blockUser(blockerId, blockedId);
        return new ResponseEntity<>("차단 완료 ❤️", HttpStatus.ACCEPTED);
    }

    // 사용자 신고하기
    @PostMapping("/user/{reporterId}/report")
    public ResponseEntity<?> reportUser(@PathVariable Long reporterId, @RequestBody ReportRequestDto reportRequestDto) {
        reportService.reportUser(reportRequestDto);
        return new ResponseEntity<>("신고가 접수되었습니다.🫡", HttpStatus.CREATED);
    }
}
