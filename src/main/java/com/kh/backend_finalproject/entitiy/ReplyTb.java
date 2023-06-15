package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime writeDate; // 작성 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num")
    private PostTb post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private UserTb user;
}
