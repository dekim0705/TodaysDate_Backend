package com.kh.backend_finalproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ReportRequestDto {
    private Long reportedId;
    private String content;
    private LocalDateTime reportDate;
}
