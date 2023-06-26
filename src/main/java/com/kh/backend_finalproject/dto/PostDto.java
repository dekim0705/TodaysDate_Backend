package com.kh.backend_finalproject.dto;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long postId;
    private Long userId;
    private String pfImg;
    private String nickname;
    private String title;
    private String district;
    private int bookmarkCount;
    private int viewCount;
    private String course;
    private String theme;
    private List<String> comment;
    private List<PinDto> pins;
    private List<String> placeTag;
    private String content;
    private String imgUrl;
    private LocalDateTime writeDate;
}
