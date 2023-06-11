package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("메인 페이지 - 전체 지역 게시글 작성일 최근순 정렬 테스트")
    public void findAllPostsWithUserDetailsTest() {
        List<Object[]> postList = postRepository.findAllPostsWithUserDetails();
        List<PostUserDto> postDetailList = postList.stream()
                .map(post -> {
                    PostUserDto dto = new PostUserDto();
                    dto.setPfImg((String)post[0]);
                    dto.setNickname((String)post[1]);
                    dto.setTitle((String)post[2]);
                    dto.setDistrict((String)post[3]);
                    dto.setThumbnail((String)post[4]);
                    dto.setWriteDate(((Timestamp) post[5]).toLocalDateTime());
                    return dto;
                })
                .collect(Collectors.toList());
        for(PostUserDto e : postDetailList) {
            System.out.println("프로필 : " + e.getPfImg());
            System.out.println("닉네임 : " + e.getNickname());
            System.out.println("제목 : " + e.getTitle());
            System.out.println("상세 지역 : " + e.getDistrict());
            System.out.println("썸네일 : " + e.getThumbnail());
            System.out.println("작성일 : " + e.getWriteDate());
        }
    }
    @Test
    @DisplayName("메인 페이지 - 특정 지역 게시글 작성일 최근순 정렬 테스트")
    public void findAllPostsWithUserDetailsTest2() {
        List<Object[]> postList = postRepository.findAllPostsWithUserDetails(RegionStatus.SEOUL.name());
        List<PostUserDto> postDetailList = postList.stream()
                .map(post -> {
                    PostUserDto dto = new PostUserDto();
                    dto.setPfImg((String)post[0]);
                    dto.setNickname((String)post[1]);
                    dto.setTitle((String)post[2]);
                    dto.setDistrict((String)post[3]);
                    dto.setThumbnail((String)post[4]);
                    dto.setWriteDate(((Timestamp) post[5]).toLocalDateTime());
                    return dto;
                })
                .collect(Collectors.toList());
        for(PostUserDto e : postDetailList) {
            System.out.println("프로필 : " + e.getPfImg());
            System.out.println("닉네임 : " + e.getNickname());
            System.out.println("제목 : " + e.getTitle());
            System.out.println("상세 지역 : " + e.getDistrict());
            System.out.println("썸네일 : " + e.getThumbnail());
            System.out.println("작성일 : " + e.getWriteDate());
        }
    }
    @Test
    @DisplayName("메인 페이지 - 키워드 검색 테스트")
    public void findByKeywordTest() {
        List<PostTb> postList = postRepository.findByKeyword("엔터테인먼트");
        for(PostTb e : postList) {
            System.out.println("📍: " + e.getTitle());
        }
    }
    @Test
    @DisplayName("메인 페이지 - 상위 북마크 5개 게시글 내림차순(북마크 수, 제목) 테스트")
    public void findTop5ByBookmarkCountTest() {
        List<Object[]> list = postRepository.findTop5ByBookmarkCount();
        List<PostBookmarkDto> postBookmarkList = list.stream()
                .map(rank -> {
                    PostBookmarkDto dto = new PostBookmarkDto();
                    dto.setTitle((String)rank[0]);
                    dto.setBookmarkCount(((BigInteger)rank[1]).intValue());
                    return dto;
                }).collect(Collectors.toList());
        for(PostBookmarkDto e : postBookmarkList) {
            System.out.println("제목 : " + e.getTitle());
            System.out.println("북마크 수 : " + e.getBookmarkCount());
        }
    }
}