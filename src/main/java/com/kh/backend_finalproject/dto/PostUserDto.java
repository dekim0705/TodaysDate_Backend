package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class PostUserDto {
    private String pfImg;
    private String nickname;
    private String title;
    private String district;
    private String thumbnail;
    private LocalDateTime writeDate;
}
