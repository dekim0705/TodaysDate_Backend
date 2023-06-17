package com.kh.backend_finalproject.entitiy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenTb {
    @Id
    @GeneratedValue
    @Column(name = "token_num")
    private Long id;             // 토큰 번호

    @Column(nullable = false)
    private Long userId;         // UserTb Primary Key

    @Column(nullable = false)
    private String refreshToken; // 리프레시 토큰

    public RefreshTokenTb(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }

    public RefreshTokenTb update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}