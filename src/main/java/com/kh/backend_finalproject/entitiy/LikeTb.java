package com.kh.backend_finalproject.entitiy;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class LikeTb {
    @Id
    @GeneratedValue
    @Column(name = "like_num")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_num")
    private UserTb user;
}
