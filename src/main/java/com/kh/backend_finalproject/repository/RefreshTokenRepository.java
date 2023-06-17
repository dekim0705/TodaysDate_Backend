package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.entitiy.RefreshTokenTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenTb, Long> {
    Optional<RefreshTokenTb> findByUserId(Long userId);
    Optional<RefreshTokenTb> findByRefreshToken(String refreshToken);
}
