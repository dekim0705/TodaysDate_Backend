package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.BookmarkRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

    // ✍️전체 지역 게시글 작성일 최근순 정렬
    public List<PostUserDto> getAllPostsList() {
        List<PostUserDto> postUserDtos = postRepository.getAllPostsWithUserDetails();
        return postUserDtos;
    }
}
