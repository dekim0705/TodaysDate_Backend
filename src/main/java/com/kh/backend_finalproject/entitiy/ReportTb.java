package com.kh.backend_finalproject.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class ReportTb {
    @Id
    @GeneratedValue
    @Column(name = "report_num")
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content; // 신고 내용

    private LocalDateTime reportDate; // 신고 날짜

    @Column(name = "reporter_fk")
    private String reporter; // 신고자

    @Column(name = "reported_fk")
    private String reported; // 신고받은 사람
}
