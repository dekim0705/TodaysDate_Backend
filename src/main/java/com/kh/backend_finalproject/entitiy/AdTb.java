package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
public class AdTb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ad_num")
    private Long id;

    @Column(length = 100)
    private String adName;   // 광고 이름

    @Column(length = 500)
    private String imgUrl; // 광고 이미지
}
