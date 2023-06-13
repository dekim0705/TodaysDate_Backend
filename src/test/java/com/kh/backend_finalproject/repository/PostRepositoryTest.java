package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Test
    @DisplayName("전체 지역 게시글 작성일 최근순 정렬 테스트")
    public void findAllPostsWithUserDetailsTest() {
        List<PostUserDto> postUserDtos = postRepository.findAllPostsWithUserDetails();
        for(PostUserDto e : postUserDtos) {
            System.out.println("🦄제목 : " + e.getTitle());
            System.out.println("🦄닉네임 : " + e.getNickname());
        }
    }
    @Test
    @DisplayName("특정 지역 게시글 작성일 최근순 정렬 테스트")
    public void findRegionPostsWithUserDetailsTest() {
        List<PostUserDto> postUserDtos = postRepository.findRegionPostsWithUserDetails(RegionStatus.SEOUL);
        for(PostUserDto e : postUserDtos) {
            System.out.println("🦄제목 : " + e.getTitle());
            System.out.println("🦄닉네임 : " + e.getNickname());
        }
    }
    @Test
    @DisplayName("키워드 검색 테스트")
    public void findByKeywordTest() {
        List<PostTb> postList = postRepository.findByKeyword("경복궁");
        for(PostTb e : postList) {
            System.out.println("🦄제목 : " + e.getTitle());
        }
    }
    @Test
    @DisplayName("북마크 상위 5개 게시글 내림차순 정렬 테스트")
    public void findTop5ByBookmarkCountTest() {
        Pageable topFive = PageRequest.of(0,5);
        Page<PostBookmarkDto> postBookmarkDtos = postRepository.findTop5ByBookmarkCount(topFive);
        for(PostBookmarkDto e : postBookmarkDtos) {
            System.out.println("🦄️ 제목 : " + e.getTitle());
            System.out.println("🦄️ 북마크 수 : " + e.getBookmarkCount());
        }
    }
    @Test
    @DisplayName("Id로 게시글 유무 확인 테스트")
    public void findByIdTest() {
        Optional<PostTb> post = postRepository.findById(1L);
        System.out.println("🦄있으면 false : " + post.isEmpty());
    }
}