package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.BookmarkRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

    // ✅️전체 지역 게시글 작성일 최근순 정렬
    public List<PostUserDto> findAllPostsList() {
        List<PostUserDto> postUserDtos = postRepository.findAllPostsWithUserDetails();
        return postUserDtos;
    }
    // ✅️특정 지역 게시글 작성일 최근순 정렬
    public List<PostUserDto> findRegionPostsList(RegionStatus status) {
        List<PostUserDto> postUserDtos = postRepository.findRegionPostsWithUserDetails(status);
        return postUserDtos;
    }
    // ✅키워드 검색
    public List<PostTb> findByKeyword(String keyword) {
        List<PostTb> postList = postRepository.findByKeyword(keyword);
        return postList;
    }
    // ✅북마크 상위 5개 게시글 내림차순 정렬
    public Page<PostBookmarkDto> findTop5ByBookmarkCount() {
        Pageable topFive = PageRequest.of(0,5);
        Page<PostBookmarkDto> postBookmarkDtos = postRepository.findTop5ByBookmarkCount(topFive);
        return postBookmarkDtos;
    }
    // ✅회원 프로필 가져오기(by Email)
    public String findPfImgByEmail(String email) {
        UserTb user = userRepository.findByEmail(email);
        return user.getPfImg();
    }
}
