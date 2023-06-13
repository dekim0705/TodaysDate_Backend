package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.IsPush;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class PostUserDto {
    private Long id;
    private String pfImg;
    private String nickname;
    private String title;
    private String district;
    private String thumbnail;
    private LocalDateTime writeDate;

    public PostUserDto(Long id, String nickname, String title, LocalDateTime writeDate) {
        this.id = id;
        this.nickname = nickname;
        this.title = title;
        this.writeDate = writeDate;
    }
    public PostUserDto(String pfImg, String nickname, String title, String district,String thumbnail, LocalDateTime writeDate) {
        this.pfImg =pfImg;
        this.nickname = nickname;
        this.title = title;
        this.district=district;
        this.thumbnail=thumbnail;
        this.writeDate = writeDate;
    }
}
