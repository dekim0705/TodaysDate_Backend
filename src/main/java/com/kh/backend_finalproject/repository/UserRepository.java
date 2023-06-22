package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<UserTb, Long> {
    Optional<UserTb> findByEmail(String email);

    Optional<UserTb> findById(Long id);

    // ✅관심지역이 같은 사용자 조회
    List<UserTb> findByUserRegion(RegionStatus region);

    //💗 관리자 페이지 : 회원 검색 (닉네임 검색)
    @Query("SELECT u FROM UserTb u WHERE u.nickname LIKE %:keyword%")
    List<UserTb> findByKeywordUser(@Param("keyword") String keyword);

    Optional<UserTb> findByNickname(String nickname);

    boolean existsByEmail(String email);
}
