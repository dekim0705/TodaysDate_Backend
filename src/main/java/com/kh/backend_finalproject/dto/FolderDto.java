package com.kh.backend_finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderDto {
    private Long id;
    private String name;
    private Long userId;
    private List<BookmarkDto> bookmarks;
}