package com.kh.backend_finalproject.entitiy;
import javax.persistence.*;

@Entity
@IdClass(BookmarkId.class)
public class BookmarkTb {
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num_fk")
    private UserTb user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num_fk")
    private PostTb post;
}
