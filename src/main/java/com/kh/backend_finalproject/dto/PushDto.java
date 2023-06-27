package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.UserTb;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PushDto {
    private Long pushId;
    private LocalDateTime sendDate;
    private Long userId;
    private Long postId;
    private String title;
    private RegionStatus userRegion;
}
