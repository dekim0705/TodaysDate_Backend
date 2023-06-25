package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class PostBookmarkDto {
    private String title;
    private Long bookmarkCount;
    private Long id;
}
