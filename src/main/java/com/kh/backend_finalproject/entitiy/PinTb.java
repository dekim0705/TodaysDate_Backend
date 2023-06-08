package com.kh.backend_finalproject.entitiy;
import javax.persistence.*;
import java.util.List;

@Entity
public class PinTb {
    @Id
    @Column(name = "pin_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pinNum;                         // 핀 번호

    private double latitude;                    // 위도
    private double longitude;                   // 경도
    private int routeNum;                       // 경로 순서

    // ✴️N:1(게시글 한 개당 여러 개의 핀)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num_fk")
    private PostTb post;
}
