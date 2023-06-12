package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
public class ChatbotTb {
    @Id
    @GeneratedValue
    @Column(name = "inquiry_num")
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;            // 문의 내용

    private LocalDateTime inquiryDate; // 문의 날짜

    private String email;              // 알림 받을 이메일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private UserTb user;
}
