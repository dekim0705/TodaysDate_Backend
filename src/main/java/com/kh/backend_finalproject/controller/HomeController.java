package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.service.HomeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    // ✅️특정 사용자가 차단한 사용자를 제외한 전체 지역의 모든 게시글..작성일 최근순 정렬
    @GetMapping(value = "/user/{blockerId}/posts")
    public ResponseEntity<List<PostUserDto>> getAllPosts(@PathVariable Long blockerId) {
        List<PostUserDto> postUserDtos = homeService.findAllPostsList(blockerId);
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // ✅특정 지역 게시글 작성일 최근순 정렬
    @GetMapping(value = "/user/{blockerId}/posts/{status}")
    public ResponseEntity<List<PostUserDto>> getRegionPosts(@PathVariable RegionStatus status, @PathVariable Long blockerId) {
        List<PostUserDto> postUserDtos = homeService.findRegionPostsList(status, blockerId);
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // 🚧키워드 검색
    @GetMapping(value = "/posts/search")
    public ResponseEntity<List<PostUserDto>> getSearchPosts(@RequestParam String keyword) {
        List<PostUserDto> posts = homeService.findByKeyword(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    // ✅북마크 상위 5개 게시글 내림차순 정렬
    @GetMapping(value = "/rank")
    public ResponseEntity<Page<PostBookmarkDto>> getTop5ByBookmark() {
        Page<PostBookmarkDto> postBookmarkDtos = homeService.findTop5ByBookmarkCount();
        return new ResponseEntity<>(postBookmarkDtos, HttpStatus.OK);
    }
    // ✅회원 프로필 가져오기(by Eamil)
    @PostMapping(value = "/profile")
    public ResponseEntity<String> getPfImgByEmail(@RequestParam String email) {
        String pfImg = homeService.findPfImgByEmail(email);
        if(pfImg != null) return new ResponseEntity<>(pfImg, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    // ✅북마크 추가
    @PostMapping(value = "/user/{userId}/post/{postId}/folder/{folderName}/bookmark")
    public ResponseEntity<Boolean> addBookmark(@PathVariable Long userId, @PathVariable Long postId, @PathVariable String folderName) {
        boolean isAddBookmark = homeService.createBookmark(userId, postId, folderName);
        if(isAddBookmark) return new ResponseEntity<>(true, HttpStatus.OK);
        else return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
    }
    // ✅광고 전체 가져오기
    @GetMapping(value = "/ads")
    public ResponseEntity<List<AdTb>> getAllAds() {
        List<AdTb> ads = homeService.findAllAd();
        if(ads != null) return new ResponseEntity<>(ads, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
