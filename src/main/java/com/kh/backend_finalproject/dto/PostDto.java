package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class PostDto {
    private String title;
    private RegionStatus region;
    private String course;
    private String theme;
    private String district;
    private List<String> comment;
    private List<String> placeTag;
    private List<String> imgUrl;
    private int viewCount;
    private int reportCount;
    private String content;
}
