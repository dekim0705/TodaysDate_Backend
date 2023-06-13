package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter @AllArgsConstructor
public class ReplyDto {
    private Long replyNum;
    private String content;
    private LocalDateTime writeDate;
}
