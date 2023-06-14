package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class PinTb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pin_num")
    private Long id;                            // 핀 번호

    private double latitude;                    // 위도
    private double longitude;                   // 경도
    private int routeNum;                       // 경로 순서

    /* 🦄'한 개의 게시글'에 '여러 개의 경로 핀'이 생성, N:1 매핑 설정! */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_num")
    private PostTb post;
}
