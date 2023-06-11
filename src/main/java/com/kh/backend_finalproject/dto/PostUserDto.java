package com.kh.backend_finalproject.dto;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter
public class PostUserDto {
    private String pfImg;
    private String nickname;
    private String title;
    private String district;
    private String thumbnail;
    private LocalDateTime writeDate;
}
