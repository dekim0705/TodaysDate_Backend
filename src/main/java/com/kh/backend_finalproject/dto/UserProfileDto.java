package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.IsMembership;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserProfileDto {
    private String nickname;
    private String userComment;
    private String PfImg;
    private IsMembership isMembership;
    private Long PostCount;
    private Long ReplyCount;
}
