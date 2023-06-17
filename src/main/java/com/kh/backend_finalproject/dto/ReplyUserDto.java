package com.kh.backend_finalproject.dto;
import lombok.*;

import java.time.LocalDateTime;


@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReplyUserDto {
    private String nickname;
    private Long replyNum;
    private String content;
    private LocalDateTime writeDate;
    private Long userNum;
    private String pfImg;

    public ReplyUserDto(String nickname, String content, LocalDateTime writeDate, String pfImg, Long userNum) {
        this.nickname = nickname;
        this.content = content;
        this.writeDate = writeDate;
        this.pfImg = pfImg;
        this.userNum = userNum;
    }

    public ReplyUserDto(String content) {
        this.content = content;
    }
}
