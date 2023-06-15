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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    // 💗 전체 회원 조회
    @GetMapping("/user")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userList = adminService.findAllUserList();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // 💗 전체 게시글 조회
    @GetMapping("/post")
    public ResponseEntity<List<PostUserDto>> getAllPosts() {
        List<PostUserDto> postList = adminService.findAllPostList();
        return new ResponseEntity<>(postList,HttpStatus.OK);
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

    // 💗 전체 광고 조회
    @GetMapping("/ad")
    public ResponseEntity<List<AdDto>> getAllAds() {
        List<AdDto> adList = adminService.findAllAdList();
        return new ResponseEntity<>(adList,HttpStatus.OK);
    }

    // 💗 전체 문의 내역 조회 (최근순 정렬)
    @GetMapping("/inquiry")
    public ResponseEntity<List<ChatbotUserDto>> getAllInquiries() {
        List<ChatbotUserDto> inquiryList = adminService.findAllInquiryList();
        return new ResponseEntity<>(inquiryList,HttpStatus.OK);
    }

    // 💗 전체 신고 내역 조회
    @GetMapping("/report")
    public ResponseEntity<List<ReportDto>> getAllReports() {
        List<ReportDto> reportList = adminService.findAllReportList();
        return new ResponseEntity<>(reportList,HttpStatus.OK);
    }

    // 💗 다중 회원 삭제
    @DeleteMapping("/delete/users")
    public ResponseEntity<String> deleteMultipleUsers(@RequestBody List<Long> userIds) {
        adminService.deleteUsers(userIds);
        return ResponseEntity.ok("회원 삭제 성공!");
    }

    //💗 다중 게시글 삭제
    @DeleteMapping("/delete/posts")
    public ResponseEntity<String> deleteMultiplePosts(@RequestBody List<Long> postIds) {
        try {
            adminService.deletePosts(postIds);
            return ResponseEntity.ok("게시글 삭제 성공!");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.badRequest().body("게시글이 존재하지 않아용~~ㅜㅜ");
        }
    }

    //💗 다중 댓글 삭제
    @DeleteMapping("/delete/replies")
    public ResponseEntity<String> deleteMultipleReplies(@RequestBody List<Long> replyIds) {
        adminService.deleteReplies(replyIds);
        return ResponseEntity.ok("댓글 삭제 성공!");
    }

    //💗 광고 삭제
    @DeleteMapping("/delete/ad")
    public ResponseEntity<String> deleteMultipleAds(@RequestBody List<Long> adIds) {
        adminService.deleteAds(adIds);
        return ResponseEntity.ok("광고 삭제 성공!");
    }

    //💗 관리자 - 회원 검색
    @GetMapping(value = "/user/search")
    public ResponseEntity<List<UserDto>> getSearchUser(@RequestParam String keyword) {
        List<UserDto> user = adminService.findByKeywordUser(keyword);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //💗 관리자 - 게시글 검색
    @GetMapping(value = "/posts/search")
    public ResponseEntity<List<PostUserDto>> getSearchPosts(@RequestParam String keyword) {
        List<PostUserDto> posts = adminService.findByKeywordAdminPost(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //💗 관리자 - 댓글 검색
    @GetMapping(value = "/replies/search")
    public ResponseEntity<List<ReplyUserDto>> getSearchReplies(@RequestParam String keyword) {
        List<ReplyUserDto> replies = adminService.findByKeywordReply(keyword);
        return new ResponseEntity<>(replies, HttpStatus.OK);
    }
}


