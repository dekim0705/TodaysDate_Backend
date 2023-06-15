package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.service.UserService;
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
@RequestMapping("/mypage")
public class UserController {
    @Autowired
    UserService userService;

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    @PostMapping(value = "/profile")
    public ResponseEntity<List<UserProfileDto>> getUserProfileBar(@RequestParam String email) {
        List<UserProfileDto> profileDtos = userService.getUserProfileInfo(email);
            return new ResponseEntity<>(profileDtos, HttpStatus.OK);
    }
    // ✅ 마이페이지 - 회원의 모든 게시글 가져오기
    @GetMapping(value = "/posts")
    public ResponseEntity<List<UserDto>> getAllPosts(@RequestParam("email") String email) {
        List<UserDto> posts = userService.getAllUserPosts(email);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }

    // ✅ 마이페이지 - 회원의 모든 댓글 가져오기
    @GetMapping(value = "/replies")
    public ResponseEntity<List<UserDto>> getAllReplies(@RequestParam("email") String email) {
        List<UserDto> replies = userService.getAllUserReplies(email);
        return new ResponseEntity<>(replies,HttpStatus.OK);
    }
    // ✅ 마이페이지 - 회원의 멤버십 상태 조회
    @GetMapping("/membership-status")
    public ResponseEntity<IsMembership> getMembershipStatus(@RequestParam("email") String email) {
        IsMembership membershipStatus = userService.getUserMembershipStatus(email); {
            return ResponseEntity.ok(membershipStatus);
        }
    }
    // ✅ 마이페이지 - 회원의 푸쉬알림 상태 조회
    @GetMapping("/notification-status")
    public ResponseEntity<IsPush> getNotificationStatus(@RequestParam("email") String email) {
        IsPush notificationStatus = userService.getUserNotificationStatus(email); {
            return ResponseEntity.ok(notificationStatus);
        }
    }
    // ✅ 마이페이지 - 회원의 푸쉬알림 상태 변경
    @PutMapping(value = "/notification-status")
    public ResponseEntity<IsPush> updateNotificationStatus(@RequestParam("email") String email) {
            IsPush updateNotificationStatus = userService.updateUserNotificationStatus(email);
            return new ResponseEntity<>(updateNotificationStatus, HttpStatus.OK);
    }

    // ✅ 마이페이지 - 회원의 북마크 폴더 가져오기
    @GetMapping(value = "/bookmark-folders")
    public ResponseEntity<List<FolderDto>> getBookmarkFolders(@RequestParam("email") String email) {
        List<FolderDto> folderDtos = userService.getUserBookmarkFolders(email);
        return new ResponseEntity<>(folderDtos, HttpStatus.OK);
    }

    // ✅ 마이페이지 - 회원의 북마크 가져오기
    @GetMapping("/bookmark-folders/{folderId}/bookmarks")
    public ResponseEntity<List<BookmarkDto>> getBookmarksInFolder(
            @PathVariable("folderId") Long folderId,
            @RequestParam("email") String email
    ) {
        List<BookmarkDto> bookmarks = userService.getBookmarksInFolder(folderId, email);
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    // ✅ 마이페이지 - 회원정보 수정
    @PutMapping("/information")
    public ResponseEntity<?> updateUserInformation(@RequestParam Long userId, @RequestBody UserDto userDto) {
        try {
            boolean isUpdate = userService.updateInformation(userId, userDto);
            return new ResponseEntity<>("회원정보 수정 성공! ❣️", HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("회원정보 수정 실패.. 😰", HttpStatus.BAD_REQUEST);
        }
    }

    // ✅ 마이페이지 - 비밀번호 변경
    @PutMapping("/pwd")
    public ResponseEntity<?> updateUserPwd(@RequestParam Long userId, @RequestBody UserTb userTb) {
        try {
            boolean isUpdate = userService.updatePwd(userId, userTb);
            return ResponseEntity.ok("비밀번호 변경 성공! ❣️");
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("비밀번호 변경 실패.. 😰" + e.getMessage());
        }
    }

    // ✅ 마이페이지 - 회원 탈퇴
    @DeleteMapping(value = "/information/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return new ResponseEntity<>("회원 탈퇴 성공! ❣️", HttpStatus.ACCEPTED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("회원 탈퇴 실패.. 😰" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
