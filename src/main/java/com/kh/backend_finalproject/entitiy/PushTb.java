package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @ToString
public class PushTb {
    @Id
    @GeneratedValue
    @Column(name = "push_num")
    private Long id;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime sendDate; // 발송 날짜

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num_fk")
    private UserTb user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num_fk")
    private PostTb post;
}
