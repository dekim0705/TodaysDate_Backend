package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.service.HomeService;
import com.kh.backend_finalproject.service.PushService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
@RequestMapping("/home")
@CrossOrigin(origins = "http://localhost:3000")
public class HomeController {
    @Autowired
    HomeService homeService;

    @Autowired
    PushService pushService;

    // 🔐️특정 사용자가 차단한 사용자의 게시글 제외 전체 지역 게시글 작성일 최근순 정렬 (SecurityContext 적용 OK)
    @GetMapping(value = "/posts")
    public ResponseEntity<List<PostUserDto>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails,
                                                         HttpServletRequest request) {
        List<PostUserDto> postUserDtos = homeService.findAllPostsList(request, userDetails);
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // 🔐특정 사용자가 차단한 사용자를 제외한 특정 지역 게시글 작성일 최근순 정렬 (SecurityContext 적용 OK)
    @GetMapping(value = "/posts/{status}")
    public ResponseEntity<List<PostUserDto>> getRegionPosts(@PathVariable RegionStatus status,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            HttpServletRequest request) {
        List<PostUserDto> postUserDtos = homeService.findRegionPostsList(status, request, userDetails);
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // 🔐키워드 검색 (SecurityContext 적용 OK)
    @GetMapping(value = "/posts/search")
    public ResponseEntity<List<PostUserDto>> getSearchPosts(@RequestParam String keyword,
                                                            @AuthenticationPrincipal UserDetails userDetails,
                                                            HttpServletRequest request) {
        List<PostUserDto> posts = homeService.findByKeyword(keyword, request, userDetails);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    // 🔐북마크 상위 5개 게시글 내림차순 정렬 (SecurityContext 적용 OK)
    @GetMapping(value = "/rank")
    public ResponseEntity<Page<PostBookmarkDto>> getTop5ByBookmark(@AuthenticationPrincipal UserDetails userDetails,
                                                                   HttpServletRequest request) {
        Page<PostBookmarkDto> postBookmarkDtos = homeService.findTop5ByBookmarkCount(request, userDetails);
        return new ResponseEntity<>(postBookmarkDtos, HttpStatus.OK);
    }
    //  🔐회원 정보 가져오기 (SecurityContext 적용 OK)
    @GetMapping(value = "/userInfo")
    public ResponseEntity<UserDto> getPfImg(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        UserDto userDto = homeService.findPfImgById(request, userDetails);
        if(userDto != null) return new ResponseEntity<>(userDto, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // 🔐북마크 추가 (SecurityContext 적용 OK)
    @PostMapping(value = "/post/{postId}/folder/{folderName}/bookmark")
    public ResponseEntity<?> addBookmark(@PathVariable Long postId, @PathVariable String folderName,
                                               @AuthenticationPrincipal UserDetails userDetails,
                                               HttpServletRequest request) {
        boolean isAddBookmark = homeService.createBookmark(postId, folderName, request, userDetails);
        if(isAddBookmark) return new ResponseEntity<>("북마크 추가 성공 ❤️", HttpStatus.OK);
        else return new ResponseEntity<>("북마크 추가 실패 🚨", HttpStatus.NO_CONTENT);
    }
    // 🔐북마크 삭제 (SecurityContext 적용 OK)
    @DeleteMapping(value = "/post/{postId}/folder/{folderName}/bookmark")
    public ResponseEntity<?> deleteBookmark(@PathVariable Long postId, @PathVariable String folderName,
                                            @AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        boolean isDeleteBookmark = homeService.deleteBookmark(postId, folderName, request, userDetails);
        if(isDeleteBookmark) return new ResponseEntity<>("북마크 삭제 성공 ❤️", HttpStatus.OK);
        else return new ResponseEntity<>("북마크 삭제 실패 🚨", HttpStatus.BAD_REQUEST);
    }
    // 🔐광고 전체 가져오기 (SecurityContext 적용 OK)
    @GetMapping(value = "/ads")
    public ResponseEntity<List<AdTb>> getAllAds(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<AdTb> ads = homeService.findAllAd(request, userDetails);
        if(ads != null) return new ResponseEntity<>(ads, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    // 🔐해당 사용자의 모든 알림 목록 가져오기 (SecurityContext 적용 OK)
    @GetMapping(value = "/pushes")
    public ResponseEntity<List<PushDto>> getAllPushList(@AuthenticationPrincipal UserDetails userDetails, HttpServletRequest request) {
        List<PushDto> pushes = pushService.fetchAllPushList(request, userDetails);
        return new ResponseEntity<>(pushes, HttpStatus.OK);
    }
    // 🔐알림 목록 삭제하기 (SecurityCOntext 적용 OK)
    @DeleteMapping(value = "/push/{pushId}")
    public ResponseEntity<?> deletePush(@PathVariable Long pushId, @AuthenticationPrincipal UserDetails userDetails,
                                        HttpServletRequest request) throws IllegalAccessException {
        try {
            pushService.deletePush(pushId, request, userDetails);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalAccessException e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
