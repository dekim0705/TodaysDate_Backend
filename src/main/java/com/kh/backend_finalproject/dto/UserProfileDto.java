package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.IsMembership;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class UserProfileDto {
    private String nickname;
    private String userComment;
    private String PfImg;
    private IsMembership isMembership;
    private int PostCount;
    private int ReplyCount;
}
