package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
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

    // ✅️전체 지역 게시글 작성일 최근순 정렬
    @GetMapping(value = "/posts")
    public ResponseEntity<List<PostUserDto>> getAllPosts() {
        List<PostUserDto> postUserDtos = homeService.getAllPostsList();
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // ✅특정 지역 게시글 작성일 최근순 정렬
    @GetMapping(value = "/posts/{status}")
    public ResponseEntity<List<PostUserDto>> getRegionPosts(@PathVariable RegionStatus status) {
        List<PostUserDto> postUserDtos = homeService.getRegionPostsList(status);
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
    // ✅키워드 검색
    @GetMapping(value = "/posts/search")
    public ResponseEntity<List<PostTb>> searchPosts(@RequestParam String keyword) {
        List<PostTb> posts = homeService.getByKeyword(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    // ✅북마크 상위 5개 게시글 내림차순 정렬
    @GetMapping(value = "/rank")
    public ResponseEntity<Page<PostBookmarkDto>> top5ByBookmarkCount() {
        Page<PostBookmarkDto> postBookmarkDtos = homeService.getTop5ByBookmarkCount();
        return new ResponseEntity<>(postBookmarkDtos, HttpStatus.OK);
    }
}
