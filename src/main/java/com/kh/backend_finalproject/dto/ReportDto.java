package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class ReportDto {
    private Long reportNum;
    private String content; // 신고 내용
    private String reporter; // 신고자
    private String reported;
    private LocalDateTime reportDate; //신고 날짜

}



