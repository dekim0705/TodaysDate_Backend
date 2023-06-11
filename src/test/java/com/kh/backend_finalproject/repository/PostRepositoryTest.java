package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("메인 페이지 - 지역명으로 게시글 조회 테스트")
    public void findByRegionTest() {
        List<PostTb> postList = postRepository.findByRegion(RegionStatus.SEOUL);
        for(PostTb e : postList) {
            System.out.println("📍 : " + e.getTitle());
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
}