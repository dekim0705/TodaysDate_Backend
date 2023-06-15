package com.kh.backend_finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookmarkDto {
    private Long id;
    private Long postId;
    private String imgUrl;
    private String title;
    private String district;
}
