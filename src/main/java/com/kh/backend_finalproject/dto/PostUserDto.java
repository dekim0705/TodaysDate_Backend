package com.kh.backend_finalproject.dto;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class PostUserDto {
    private Long id;
    private Long postId;
    private String pfImg;
    private String nickname;
    private String title;
    private String district;
    private String thumbnail;
    private LocalDateTime writeDate;
    private boolean isBlocked;

    public PostUserDto(Long id, Long postId, String pfImg, String nickname, String title, String district,
                       String thumbnail, LocalDateTime writeDate) {
        this.id = id;
        this.postId = postId;
        this.pfImg = pfImg;
        this.nickname = nickname;
        this.title = title;
        this.district = district;
        this.thumbnail = thumbnail;
        this.writeDate = writeDate;
    }

    public PostUserDto(Long id, String nickname, String title, LocalDateTime writeDate) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.writeDate = writeDate;
    }
    public PostUserDto(String pfImg, String nickname, String title, String district,String thumbnail,
                       LocalDateTime writeDate) {
        this.pfImg =pfImg;
        this.nickname = nickname;
        this.title = title;
        this.district=district;
        this.thumbnail=thumbnail;
        this.writeDate = writeDate;
    }
}
