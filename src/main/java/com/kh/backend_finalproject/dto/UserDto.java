package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.CityStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String email;
    private String pwd;
    private String nickname;
    private String userComment;
    private String pfImg;
    private CityStatus userCity;
    private char isPushOk;
    private char isMembership;
    private String authKey;
    private char isActive;
}
