package com.kh.backend_finalproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FolderDto {
    private Long id;
    private String name;
    private List<BookmarkDto> bookmarks;
    public void setBookmarks(List<BookmarkDto> bookmarkDtos) {
        this.bookmarks = bookmarkDtos;

    }
}