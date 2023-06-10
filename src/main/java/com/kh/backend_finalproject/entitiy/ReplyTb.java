package com.kh.backend_finalproject.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
public class ReplyTb {
    @Id
    @GeneratedValue
    @Column(name = "reply_num")
    private Long id;

    @Column(nullable = false, length = 2000)
    private String content;          // 댓글 내용

    private LocalDateTime writeDate; // 작성 날짜

    @ManyToOne
    @JoinColumn(name = "post_num")
    private PostTb post;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserTb user;
}
