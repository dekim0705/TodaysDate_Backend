package com.kh.backend_finalproject.entitiy;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter @ToString
public class AdTb {
    @Id
    @GeneratedValue
    @Column(name = "ad_num")
    private Long id;

    @Column(length = 100)
    private String name;   // 광고 이름

    @Column(length = 500)
    private String imgUrl; // 광고 이미지
}
