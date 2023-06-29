package com.kh.backend_finalproject.dto;
import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReplyUserDto {
    private String nickname;
    private Long id;
    private String content;
    private LocalDateTime writeDate;
    private Long userNum;
    private String pfImg;
    private String userComment;
    private Long postId;
    private boolean isBlocked;

    public ReplyUserDto(String nickname, Long id, String content, LocalDateTime writeDate, Long userNum,
                        String pfImg, String userComment, Long postId) {
        this.nickname = nickname;
        this.id = id;
        this.content = content;
        this.writeDate = writeDate;
        this.userNum = userNum;
        this.pfImg = pfImg;
        this.userComment = userComment;
        this.postId = postId;
    }

    public ReplyUserDto(Long id, String nickname, String content, LocalDateTime writeDate, String pfImg, Long userNum, String userComment) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.writeDate = writeDate;
        this.pfImg = pfImg;
        this.userNum = userNum;
        this.userComment = userComment;
    }

    public ReplyUserDto(String content) {
        this.content = content;
    }
}
