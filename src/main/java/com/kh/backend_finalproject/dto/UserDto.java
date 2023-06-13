package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.IsActive;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class UserDto {
    private String email;
    private String pwd;
    private String nickname;
    private String userComment;
    private String pfImg;
    private RegionStatus userRegion;
    private IsPush isPushOk;
    private IsMembership isMembership;
    private String authKey;
    private IsActive isActive;
    public UserDto(String pfImg, String nickname, IsPush isPushOk, String userComment) {
        this.pfImg = pfImg;
        this.nickname = nickname;
        this.isPushOk = isPushOk;
        this.userComment = userComment;
    }
}
