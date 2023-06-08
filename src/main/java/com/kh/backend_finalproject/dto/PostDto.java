package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.CityStatus;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter
public class PostDto {
    private String title;
    private CityStatus region;
    private String course;
    private String theme;
    private String district;
    private List<String> comment;
    private List<String> placeTag;
    private List<String> imgUrl;
    private int viewCount;
    private int reportCount;
    private String postContent;
}
