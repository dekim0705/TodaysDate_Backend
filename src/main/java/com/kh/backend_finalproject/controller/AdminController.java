package com.kh.backend_finalproject.controller;

import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.service.AdminService;
import com.kh.backend_finalproject.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
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
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // 💗 전체 회원 조회
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<UserDto> userList = adminService.findAllUserList(userDetails,request);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // 💗 전체 게시글 조회
    @GetMapping("/post")
    public ResponseEntity<List<PostUserDto>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<PostUserDto> postList = adminService.findAllPostList(userDetails,request);
        return new ResponseEntity<>(postList,HttpStatus.OK);
    }

    // 💗 전체 댓글 내역 조회
    @GetMapping("/reply")
    public ResponseEntity<List<ReplyUserDto>> getAllReplies(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<ReplyUserDto> replyList = adminService.findAllReplyList(userDetails,request);
        return new ResponseEntity<>(replyList,HttpStatus.OK);
    }

    // 💗 광고 추가
    @PostMapping("/ad/new")
    public ResponseEntity<AdDto> addAd(@RequestBody AdDto adDto, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        AdDto savedAdDto = adminService.createAd(adDto, userDetails, request);
        return new ResponseEntity<>(savedAdDto, HttpStatus.OK);
    }

    // 💗 전체 광고 조회
    @GetMapping("/ad")
    public ResponseEntity<List<AdDto>> getAllAds(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<AdDto> adList = adminService.findAllAdList(userDetails,request);
        return new ResponseEntity<>(adList,HttpStatus.OK);
    }

    // 💗 전체 문의 내역 조회 (최근순 정렬)
    @GetMapping("/inquiry")
    public ResponseEntity<List<ChatbotUserDto>> getAllInquiries(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<ChatbotUserDto> inquiryList = adminService.findAllInquiryList(userDetails,request);
        return new ResponseEntity<>(inquiryList,HttpStatus.OK);
    }

    // 💗 전체 신고 내역 조회
    @GetMapping("/report")
    public ResponseEntity<List<ReportDto>> getAllReports(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<ReportDto> reportList = adminService.findAllReportList(userDetails,request);
        return new ResponseEntity<>(reportList,HttpStatus.OK);
    }

    // 💗 다중 회원 삭제
    @DeleteMapping("/delete/users")
    public ResponseEntity<String> deleteMultipleUsers(@RequestBody List<Long> userIds, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        adminService.deleteUsers(userIds, userDetails, request);
        return ResponseEntity.ok("회원 삭제 성공!");
    }

    //💗 다중 게시글 삭제
    @DeleteMapping("/delete/posts")
    public ResponseEntity<String> deleteMultiplePosts(@RequestBody List<Long> postIds, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        try {
            adminService.deletePosts(postIds,userDetails,request);
            return ResponseEntity.ok("게시글 삭제 성공!");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("게시글이 존재하지 않아용~~ㅜㅜ");
        }
    }

    //💗 다중 댓글 삭제
    @DeleteMapping("/delete/replies")
    public ResponseEntity<String> deleteMultipleReplies(@RequestBody List<Long> replyIds, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        adminService.deleteReplies(replyIds, userDetails, request);
        return ResponseEntity.ok("댓글 삭제 성공!");
    }

    //💗 광고 삭제
    @DeleteMapping("/delete/ad")
    public ResponseEntity<String> deleteMultipleAds(@RequestBody List<Long> adIds, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        adminService.deleteAds(adIds, userDetails, request);
        return ResponseEntity.ok("광고 삭제 성공!");
    }

    //💗 관리자 - 회원 검색
    @GetMapping(value = "/user/search")
    public ResponseEntity<List<UserDto>> getSearchUser(@RequestParam String keyword, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<UserDto> user = adminService.findByKeywordUser(keyword, userDetails, request);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //💗 관리자 - 게시글 검색
    @GetMapping(value = "/posts/search")
    public ResponseEntity<List<PostUserDto>> getSearchPosts(@RequestParam String keyword, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<PostUserDto> posts = adminService.findByKeywordAdminPost(keyword, userDetails, request);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //💗 관리자 - 댓글 검색
    @GetMapping(value = "/replies/search")
    public ResponseEntity<List<ReplyUserDto>> getSearchReplies(@RequestParam String keyword, @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<ReplyUserDto> replies = adminService.findByKeywordReply(keyword, userDetails, request);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }

    // 💗 관리자 - 문의 상태 변경하기
    @PutMapping(value = "/inquiry/{inquiryNum}")
    public ResponseEntity<?> updateInquiryStatus(@PathVariable Long inquiryNum, @RequestParam String status,
                                                      @AuthenticationPrincipal UserDetails userDetails,
                                                      HttpServletRequest request) {
        adminService.updateInquiryStatus(inquiryNum, status, userDetails, request);
        return new ResponseEntity<>("문의 상태 변경 완료!", HttpStatus.OK);
    }
}


