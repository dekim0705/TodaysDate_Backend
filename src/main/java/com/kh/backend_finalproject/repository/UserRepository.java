package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.UserDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<UserTb, Long> {
    UserTb findByEmail(String email);

    // ✅마이 페이지 : 메인 회원 프로필 바 가져오기
    @Query("SELECT new com.kh.backend_finalproject.dto.UserDto(u.pfImg, u.nickname, u.isPush, u.userComment) " +
            "FROM UserTb u  " +
            "WHERE u.email = :email ")
    List<UserDto> findUserInfo(@Param("email") String email);
}
