package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter @AllArgsConstructor
public class ReplyUserDto {
    private String nickname;
    private Long replyNum;
    private String content;
    private LocalDateTime writeDate;

}



