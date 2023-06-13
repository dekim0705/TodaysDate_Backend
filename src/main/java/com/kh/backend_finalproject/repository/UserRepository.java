package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.UserDto;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserTb, Long> {
    UserTb findByEmail(String email);

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    @Query("SELECT new com.kh.backend_finalproject.dto.UserProfileDto(u.nickname, u.userComment, u.pfImg, u.isMembership, " +
            "(SELECT COUNT(p) FROM PostTb p WHERE p.user = u), " +
            "(SELECT COUNT(r) FROM ReplyTb r WHERE r.user = u)) " +
            "FROM UserTb u " +
            "WHERE u.email = :email")
    List<UserProfileDto> findUserProfileInfo(@Param("email") String email);
    Optional<UserTb> findById(Long id);

    // ✅관심지역이 같은 사용자 조회
    List<UserTb> findByUserRegion(RegionStatus region);

    // 마이페이지 - 회원 전체 게시글 가져오기
//    @Query("SELECT new com.kh.backend_finalproject.dto.PostDto(p.id, p.title, p.content, u.nickname, p.writeDate, p.viewCount) " +
//            "FROM PostTb p JOIN p.user u " +
//            "WHERE u.id = :userId")
//    List<PostDto> findUserPosts(@Param("userId") Long userId);

}
