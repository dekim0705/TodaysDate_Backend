package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.ReportRequestDto;
import com.kh.backend_finalproject.service.PostService;
import com.kh.backend_finalproject.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReportController {
    @Autowired
    ReportService reportService;

    // 🔐게시글 신고하기 (SecurityContext 적용 OK)
    @DeleteMapping("/post/{postId}/report")
    public ResponseEntity<?> reportPost(@PathVariable Long postId, @AuthenticationPrincipal UserDetails userDetails,
                                        HttpServletRequest request) {
        reportService.reportPost(postId, request, userDetails);
        return new ResponseEntity<>("게시글 신고 완료 ❤️", HttpStatus.ACCEPTED);
    }

    // 🔐사용자 차단하기 (SecurityContext 적용 OK)
    @PostMapping("/block/{blockedId}")
    public ResponseEntity<?> blockUser(@PathVariable Long blockedId, @AuthenticationPrincipal UserDetails userDetails,
                                       HttpServletRequest request) {
        reportService.blockUser(blockedId, request, userDetails);
        return new ResponseEntity<>("차단 완료 ❤️", HttpStatus.ACCEPTED);
    }

    // 🔐사용자 신고하기 (SecurityContext 적용 OK)
    @PostMapping("/report")
    public ResponseEntity<?> reportUser(@RequestBody ReportRequestDto reportRequestDto,
                                        @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        reportService.reportUser(reportRequestDto, request, userDetails);
        return new ResponseEntity<>("신고가 접수되었습니다.🫡", HttpStatus.CREATED);
    }

    // 🔐사용자 차단 해제하기 (SecurityContext 적용 OK)
    @DeleteMapping("/block/{blockedId}")
    public ResponseEntity<?> deleteBlockUser(@PathVariable Long blockedId,
                                             @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        reportService.deleteBlockUser(blockedId, request, userDetails);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
