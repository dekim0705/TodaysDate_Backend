package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter @Setter @AllArgsConstructor
public class PostDto {
    private String title;
    private RegionStatus region;
    private String course;
    private String theme;
    private String district;
    private List<String> comment;
    private List<String> placeTag;
    private List<String> imgUrl;
    private String content;
}
