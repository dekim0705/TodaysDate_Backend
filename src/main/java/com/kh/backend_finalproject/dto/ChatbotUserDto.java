package com.kh.backend_finalproject.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
public class ChatbotUserDto {
    private String nickname;
    private Long inquiryNum;
    private String inquiryContent;
    private LocalDateTime inquiryDate;
    private String inquiryEmail;
    private String inquiryStatus;

    public ChatbotUserDto() {

    }
}
