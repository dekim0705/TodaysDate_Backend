package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter @AllArgsConstructor
public class ReplyUserDto {
    private String nickname;
    private Long replyNum;
    private String content;
    private LocalDateTime writeDate;
    private Long userNum;
    private String pfImg;

    public ReplyUserDto(String nickname, String content, LocalDateTime writeDate, String pfImg) {
        this.nickname = nickname;
        this.content = content;
        this.writeDate = writeDate;
        this.pfImg = pfImg;
    }
}
