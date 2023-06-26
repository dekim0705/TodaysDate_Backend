package com.kh.backend_finalproject.dto;

import com.kh.backend_finalproject.constant.IsActive;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.constant.RegionStatus;

import com.kh.backend_finalproject.entitiy.FolderTb;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String email;
    private String pwd;
    private String nickname;
    private String userComment;
    private String pfImg;
    private RegionStatus userRegion;
    private LocalDateTime regDate;
    private IsPush isPushOk;
    private IsMembership isMembership;
    private String authKey;
    private IsActive isActive;
    private List<String> blockedNickname;

    // 마이페이지 나의 게시글 조회용 필드 추가 (글번호, 제목, 본문, 작성일, 조회수, 닉네임 사용)
    private Long postNum;
    private int viewCount;
    // 마이페이지 나의 댓글조회용 필드 추가 (댓글번호, 댓글내용, 본문제목, 작성일, 닉네임 사용)
    private Long replyNum;
    private String content;
    private String title;
    private LocalDateTime writeDate;

    private int postCount;
    private int replyCount;

}
