package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;
    @Test
    @DisplayName("전체 게시글 조회")
    public void findPostAllTest() {
        List<PostTb> postList = postRepository.findAll();
        for (PostTb e : postList) {
            System.out.println("💙제목 : " + e.getTitle());
        }
    }
}