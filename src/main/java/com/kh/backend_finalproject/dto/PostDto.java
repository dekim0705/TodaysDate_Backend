package com.kh.backend_finalproject.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class PostDto {
    private String pfImg;
    private String nickname;
    private LocalDateTime writeDate;
    private String title;
    private String district;
    private String imgUrl;
}
