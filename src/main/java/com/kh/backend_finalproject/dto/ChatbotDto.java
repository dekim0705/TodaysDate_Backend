package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter @AllArgsConstructor
public class ChatbotDto {
    private Long userId;
    private String inquiryEmail;
    private String inquiryContent;

    public ChatbotDto() {

    }
}
