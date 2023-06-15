package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
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
}
