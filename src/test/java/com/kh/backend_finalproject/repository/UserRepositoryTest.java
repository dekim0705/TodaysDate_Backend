//package com.kh.backend_finalproject.repository;
//import com.kh.backend_finalproject.constant.IsPush;
//import com.kh.backend_finalproject.constant.RegionStatus;
//import com.kh.backend_finalproject.dto.UserProfileDto;
//import com.kh.backend_finalproject.entitiy.BlockTb;
//import com.kh.backend_finalproject.entitiy.PostTb;
//import com.kh.backend_finalproject.entitiy.ReplyTb;
//import com.kh.backend_finalproject.entitiy.UserTb;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Optional;
//import java.util.List;
//
//@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
//@Sql("/dummy_test.sql")
//class UserRepositoryTest {
//    @Autowired
//    UserRepository userRepository;
//
////    @Test
////    @DisplayName("회원 프로필 가져오기")
////    public void findByEmailTest() {
////        UserTb user = userRepository.findByEmail("user1@naver.com");
////        System.out.println("🦄 : " + user.getPfImg());
////    }
//    @Test
//    @DisplayName("마이페이지 회원 프로필바 가져오기 테스트")
//    public void findUserProfileTest() {
//        List<UserProfileDto> user = userRepository.findUserProfileInfo("user1@naver.com");
//        for (UserProfileDto e : user) {
//            System.out.println("🍒프로필사진 : " + e.getPfImg());
//            System.out.println("🍒닉네임 : " + e.getNickname());
//            System.out.println("🍒멤버십 설정 : " + e.getIsMembership());
//            System.out.println("🍒한 줄 소개 : " + e.getUserComment());
//            System.out.println("🍒총 게시글 수 : " + e.getPostCount());
//            System.out.println("🍒총 댓글 수 : " + e.getReplyCount());
//        }
//    }
//    @Test
//    @DisplayName("Id로 게시글 유무 확인 테스트")
//    public void findByIdTest() {
//        Optional<UserTb> user = userRepository.findById(1L);
//        System.out.println("🦄 있으면 false : " + user.isEmpty());
//    }
//    @Test
//    @DisplayName("관심지역이 같은 사용자 조회 테스트")
//    public void findByUserRegionTest() {
//        List<UserTb> users = userRepository.findByUserRegion(RegionStatus.BUSAN);
//        for (UserTb e : users) {
//            System.out.println("🦄 부산 : " + e.getNickname());
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("마이페이지 회원 전체 게시글 가져오기 테스트")
//    public void findUserPostsTest() {
//        Optional<UserTb> user = userRepository.findById(1L);
//        if(user.isPresent()) {
//            List<PostTb> posts = user.get().getPosts();
//            for (PostTb post : posts) {
//                System.out.println("🍒 글번호: " + post.getId());
//                System.out.println("🍒 제목 : " + post.getTitle());
//                System.out.println("🍒 본문 : " + post.getContent());
//                System.out.println("🍒 닉네임 : " + post.getUser().getNickname());
//                System.out.println("🍒 작성일 : " + post.getWriteDate());
//                System.out.println("🍒 조회수 : " + post.getViewCount());
//            }
//        }
//    }
//
//    @Test
//    @Transactional
//    @DisplayName("마이페이지 회원 전체 댓글 가져오기 테스트")
//    public void findUserRepliesTest() {
//        Optional<UserTb> user = userRepository.findById(3L);
//        if(user.isPresent()) {
//            List<ReplyTb> replies = user.get().getReplies();
//            for(ReplyTb reply : replies) {
//                System.out.println("🍒 댓글 번호: " + reply.getId());
//                System.out.println("🍒 댓글 본문 : " + reply.getContent());
//                System.out.println("🍒 원문 제목 : " + reply.getPost().getTitle());
//                System.out.println("🍒 닉네임 : " + reply.getUser().getNickname());
//                System.out.println("🍒 작성일 : " + reply.getWriteDate());
//            }
//        }
//    }
//    @Test
//    @Transactional
//    @DisplayName("관리자페이지 - 전체 회원 조회 테스트")
//    public void findAllUserTest () {
//        List<UserTb> users = userRepository.findAll();
//        for (UserTb e : users) {
//            System.out.println("💗회원번호 : " + e.getId());
//            System.out.println("💗닉네임 : " + e.getNickname());
//            System.out.println("💗이메일 : " + e.getEmail());
//            System.out.println("💗가입일자 : " + e.getRegDate());
//            System.out.println("💗멤버십여부 : " + e.getIsMembership());
//            List<BlockTb> blockedUsers = e.getBlockedUsers();
//            if (blockedUsers != null && !blockedUsers.isEmpty()) {
//                System.out.println("💗차단회원 : " + blockedUsers.get(0).getBlocked().getNickname());
//            } else {
//                System.out.println("💗차단회원 : 없음");
//            }
//            System.out.println("————————-----------------—————— ");
//        }
//    }
//    @Test
//    @DisplayName("마이페이지 - 회원 멤버십 여부")
//    public void findUserMembershipStatusTest() {
//        UserTb user = userRepository.findByEmail("user1@naver.com");
//        System.out.println("🍒회원 멤버십 여부 (아니면 FREE) : " + user.getIsMembership());
//    }
//    @Test
//    @DisplayName("마이페이지 - 회원 알림수신 동의 여부 조회와 변경..?")
//    public void findUserNotificationStatusTest() {
//        UserTb user = userRepository.findByEmail("user1@naver.com");
//        System.out.println("🍒회원 알림수신 동의 여부 (아니면 NOPUSH) : " + user.getIsPush());
//        IsPush currentStatus = user.getIsPush();
//
//        IsPush newStatus = currentStatus.equals(IsPush.PUSH) ? IsPush.NOPUSH : IsPush.PUSH;
//        user.setIsPush(newStatus);
//        userRepository.save(user);
//        System.out.println("🍒회원 알림수신 동의 여부 변경 후 : " + user.getIsPush());
//    }
//}
