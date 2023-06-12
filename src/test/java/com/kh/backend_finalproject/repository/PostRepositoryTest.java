package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.PostUserDto;
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
    @DisplayName("전체 지역 게시글 작성일 최근순 정렬 테스트")
    public void getAllPostsWithUserDetailsTest() {
        List<PostUserDto> postUserDtos = postRepository.getAllPostsWithUserDetails();
        for(PostUserDto e : postUserDtos) {
            System.out.println("🦄제목 : " + e.getTitle());
            System.out.println("🦄닉네임 : " + e.getNickname());
        }
    }
}