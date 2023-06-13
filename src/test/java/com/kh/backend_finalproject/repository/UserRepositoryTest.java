package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("회원 프로필 가져오기")
    public void findByEmailTest() {
        UserTb user = userRepository.findByEmail("user1@naver.com");
        System.out.println("🦄 : " + user.getPfImg());
    }
    @Test
    @DisplayName("마이페이지 회원 프로필바 가져오기 테스트")
    public void findUserProfileTest() {
        List<UserProfileDto> user = userRepository.findUserProfileInfo("user1@naver.com");
        for (UserProfileDto e : user) {
            System.out.println("🍒프로필사진 : " + e.getPfImg());
            System.out.println("🍒닉네임 : " + e.getNickname());
            System.out.println("🍒멤버십 설정 : " + e.getIsMembership());
            System.out.println("🍒한 줄 소개 : " + e.getUserComment());
            System.out.println("🍒총 게시글 수 : " + e.getPostCount());
            System.out.println("🍒총 댓글 수 : " + e.getReplyCount());
        }
    }
    @Test
    @DisplayName("Id로 게시글 유무 확인 테스트")
    public void findByIdTest() {
        Optional<UserTb> user = userRepository.findById(1L);
        System.out.println("🦄 있으면 false : " + user.isEmpty());
    }
    @Test
    @DisplayName("관심지역이 같은 사용자 조회 테스트")
    public void findByUserRegionTest() {
        List<UserTb> users = userRepository.findByUserRegion(RegionStatus.BUSAN);
        for (UserTb e : users) {
            System.out.println("🦄 부산 : " + e.getNickname());
        }
    }

//    @Test
//    @DisplayName("마이페이지 회원 전체 게시글 가져오기 테스트")
//    public void findUserPostsTest() {
//        List<PostDto> userPosts = userRepository.findUserPosts(1L);
//        for (PostDto e : userPosts) {
//            System.out.println("🍒글 번호 : " + e.getPostNum());
//            System.out.println("🍒닉네임 : " + e.getNickname());
//            System.out.println("🍒제목 : " + e.getTitle());
//            System.out.println("🍒본문 : " + e.getContent());
//            System.out.println("🍒작성일 : " + e.getWriteDate());
//            System.out.println("🍒조회수 : " + e.getViewCount());
//        }
//    }

    @Test
    @Transactional
    @DisplayName("마이페이지 회원 전체 게시글 가져오기 테스트")
    public void findUserPostsTest() {
        Optional<UserTb> user = userRepository.findById(1L);
        if(user.isPresent()) {
            List<PostTb> posts = user.get().getPosts();
            for (PostTb post : posts) {
                System.out.println("🍒 제목 : " + post.getTitle());
                System.out.println("🍒 본문 : " + post.getContent());
                System.out.println("🍒 닉네임 : " + post.getUser().getNickname());
            }
        }
    }
}
