package com.kh.backend_finalproject.controller;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.service.HomeService;
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
@RequestMapping("/home")
public class HomeController {
    @Autowired
    HomeService homeService;

    // ✍️전체 지역 게시글 작성일 최근순 정렬
    @GetMapping(value = "/post/all")
    public ResponseEntity<List<PostUserDto>> getAllPosts() {
        List<PostUserDto> postUserDtos = homeService.getAllPostsList();
        return new ResponseEntity<>(postUserDtos, HttpStatus.OK);
    }
}
