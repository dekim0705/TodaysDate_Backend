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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class UserController {
    @Autowired
    UserService userService;

    // 🔐 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    @PostMapping(value = "/profile")
    public ResponseEntity<UserDto> getUserProfileBar(@AuthenticationPrincipal UserDetails userDetails,
                                                                  HttpServletRequest request) throws IllegalAccessException {
        UserDto profileDtos = userService.getUserProfileInfo(request, userDetails);
        if(profileDtos != null) return new ResponseEntity<>(profileDtos, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // 🔐 마이페이지 - 회원의 모든 게시글 가져오기
    @GetMapping(value = "/posts")
    public ResponseEntity<List<UserDto>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails,
                                                     HttpServletRequest request) {
        List<UserDto> posts = userService.getAllUserPosts(request, userDetails);
        return new ResponseEntity<>(posts,HttpStatus.OK);
    }
    // 🔐 마이페이지 - 회원의 게시글 삭제하기
    @DeleteMapping(value = "/posts")
    public ResponseEntity<?> deletePosts(@RequestBody List<Long> postIds,
                                         @AuthenticationPrincipal UserDetails userDetails,
                                         HttpServletRequest request
                                         ) throws IllegalAccessException {

        boolean isDeleted = userService.deletePosts(postIds, request, userDetails);
        if (isDeleted) {
            return new ResponseEntity<>("게시글 삭제 성공 ❣️", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("게시글 삭제 실패 .. 😰", HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐 마이페이지 - 회원의 모든 댓글 가져오기
    @GetMapping(value = "/replies")
    public ResponseEntity<List<UserDto>> getAllReplies(@AuthenticationPrincipal UserDetails userDetails,
                                                       HttpServletRequest request) {
        List<UserDto> replies = userService.getAllUserReplies(request, userDetails);
        return new ResponseEntity<>(replies,HttpStatus.OK);
    }
    // 🔐 마이페이지 - 회원의 댓글 삭제하기
    @DeleteMapping(value = "/replies")
    public ResponseEntity<?> deleteReplies(@RequestBody List<Long> replyIds,
                                           @AuthenticationPrincipal UserDetails userDetails,
                                           HttpServletRequest request
                                           ) throws IllegalAccessException {
        boolean isDeleted = userService.deleteReplies(replyIds, request, userDetails);
        if (isDeleted) {
            return new ResponseEntity<>("댓글 삭제 성공 ❣️", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("댓글 삭제 실패 .. 😰", HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐 마이페이지 - 회원의 멤버십 상태 조회 ❗️사용 안함
    @GetMapping("/membership-status")
    public ResponseEntity<IsMembership> getMembershipStatus(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        IsMembership membershipStatus = userService.getUserMembershipStatus(request, userDetails); {
            return new ResponseEntity<>(membershipStatus, HttpStatus.OK);
        }
    }
    // 🔐 마이페이지 - 회원의 푸쉬알림 상태 조회
    @GetMapping("/notification-status")
    public ResponseEntity<IsPush> getNotificationStatus(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        IsPush notificationStatus = userService.getUserNotificationStatus(request, userDetails); {
            return new ResponseEntity<>(notificationStatus, HttpStatus.OK);
        }
    }
    // 🔐 마이페이지 - 회원의 푸쉬알림 상태 변경
    @PutMapping(value = "/notification-status")
    public ResponseEntity<IsPush> updateNotificationStatus(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
            IsPush updateNotificationStatus = userService.updateUserNotificationStatus(request, userDetails);
            return new ResponseEntity<>(updateNotificationStatus, HttpStatus.OK);
    }

    // 🔐 마이페이지 - 회원의 북마크 폴더 생성하기
    @PostMapping(value="/bookmark-folders")
    public ResponseEntity<?> createBookmarkFolder(@RequestBody FolderDto folderDto,
                                                  @AuthenticationPrincipal UserDetails userDetails,
                                                  HttpServletRequest request) throws IllegalAccessException {
        boolean isFolderCreated = userService.createBookmarkFolder(folderDto, request, userDetails);
        if (isFolderCreated) return new ResponseEntity<>("폴더 생성 성공 ❣️", HttpStatus.CREATED);
        else return new ResponseEntity<>("폴더 생성 실패 .. 😰", HttpStatus.BAD_REQUEST);
    }

    // 🔐 마이페이지 - 회원의 북마크 폴더 삭제하기
    @DeleteMapping(value = "/bookmark-folders/{folderId}")
    public ResponseEntity<?> deleteBookmarkFolder(@PathVariable Long folderId, @AuthenticationPrincipal UserDetails userDetails,
                                                  HttpServletRequest request) throws IllegalAccessException {

        boolean isFolderDeleted = userService.deleteBookmarkFolder(folderId, request, userDetails);
        if (isFolderDeleted) return new ResponseEntity<>("폴더 삭제 성공 ❣️", HttpStatus.OK);
        else return new ResponseEntity<>("폴더 삭제 실패 .. 😰", HttpStatus.BAD_REQUEST);
    }

    // 🔐 마이페이지 - 회원의 북마크 폴더 이름 변경하기
    @PutMapping(value = "/bookmark-folders/{folderId}")
    public ResponseEntity<?> updateBookmarkFolderName(@PathVariable Long folderId, @RequestBody FolderDto folderDto,
                                                      @AuthenticationPrincipal UserDetails userDetails,
                                                      HttpServletRequest request) {
        boolean isFolderUpdated = userService.updateBookmarkFolderName(folderId, folderDto.getName(), request, userDetails);
        if (isFolderUpdated) {
            return new ResponseEntity<>("폴더 이름 변경 성공 ❣️", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("폴더 이름 변경 실패 .. 😰", HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐 마이페이지 - 회원의 북마크 폴더 가져오기
    @GetMapping(value = "/bookmark-folders")
    public ResponseEntity<List<FolderDto>> getBookmarkFolders(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<FolderDto> folderDtos = userService.getUserBookmarkFolders(request, userDetails);
        return new ResponseEntity<>(folderDtos, HttpStatus.OK);
    }

    // 🔐 마이페이지 - 회원의 북마크 가져오기
    @GetMapping("/bookmark-folders/{folderId}")
    public ResponseEntity<List<BookmarkDto>> getBookmarksInFolder(@PathVariable Long folderId,
                                                                  @AuthenticationPrincipal UserDetails userDetails,
                                                                  HttpServletRequest request) {
        List<BookmarkDto> bookmarks = userService.getBookmarksInFolder(folderId, request, userDetails);
        return new ResponseEntity<>(bookmarks, HttpStatus.OK);
    }

    // 🔐 마이페이지 - 회원의 북마크 폴더 이름 가져오기
    @GetMapping("/bookmarks/{folderId}")
    public ResponseEntity<String> getFolderName(@PathVariable Long folderId,
                                @AuthenticationPrincipal UserDetails userDetails,
                                HttpServletRequest request) {
        String folderName = userService.getFolderName(folderId, request, userDetails);
        return new ResponseEntity<>(folderName, HttpStatus.OK);
    }

    // 🔐 마이페이지 - 회원정보 가져오기
    @GetMapping(value = "/information")
    public ResponseEntity<UserDto> getUserInfo(@AuthenticationPrincipal UserDetails userDetails,
                                               HttpServletRequest request) {
        UserDto userDto = userService.getUserInfo(request, userDetails);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    // 🔐 마이페이지 - 회원정보 수정
    @PutMapping("/information")
    public ResponseEntity<?> updateUserInformation(@RequestBody UserDto userDto,
                                                   @AuthenticationPrincipal UserDetails userDetails,
                                                   HttpServletRequest request) {
        try {
            boolean isUpdate = userService.updateInformation(userDto, request, userDetails);
            return new ResponseEntity<>("회원정보 수정 성공! ❣️", HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("회원정보 수정 실패.. 😰", HttpStatus.BAD_REQUEST);
        }
    }

    // 🔐 마이페이지 - 비밀번호 변경
    @PutMapping("/pwd")
    public ResponseEntity<?> updateUserPwd(@RequestBody UserTb userTb,
                                           @AuthenticationPrincipal UserDetails userDetails,
                                           HttpServletRequest request) {
        try {
            boolean isUpdate = userService.updatePwd(userTb, request, userDetails);
            return ResponseEntity.ok("비밀번호 변경 성공! ❣️");
        } catch (IllegalAccessException e) {
            return ResponseEntity.badRequest().body("비밀번호 변경 실패.. 😰" + e.getMessage());
        }
    }

    // 🔐 마이페이지 - 회원 탈퇴
    @DeleteMapping(value = "/information")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        try {
            userService.deleteUser(request, userDetails);
            return new ResponseEntity<>("회원 탈퇴 성공! ❣️", HttpStatus.ACCEPTED);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>("회원 탈퇴 실패.. 😰" + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
