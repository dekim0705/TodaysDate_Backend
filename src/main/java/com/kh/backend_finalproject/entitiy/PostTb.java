package com.kh.backend_finalproject.entitiy;
import com.kh.backend_finalproject.constant.CityStatus;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PostTb {
    @Id
    @Column(name = "post_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int postNum;                            // 게시글 번호

    @Column(nullable = false, length = 500)
    private String title;                           // 제목

    @Enumerated(EnumType.STRING)
    private CityStatus region;                      // 지역

    @Column(nullable = false, length = 10)
    private String course;                          // 코스 일정

    @Column(nullable = false, length = 10)
    private String theme;                           // 코스 테마

    @Column(nullable = false, length = 20)
    private String district;                        // 상세 지역(예. 서울시 중구)

    @ElementCollection
    private List<String> comment;                   // 한 줄 평

    @Column(nullable = false, length = 500)
    @ElementCollection
    private List<String> placeTag;                  // 장소명 태그

    @ElementCollection
    @Column(length = 1000)
    private List<String> imgUrl;                    // 이미지 주소

    private int viewCount;                          // 조회수
    private LocalDateTime writeDate;                // 작성일
    private int reportCount;                        // 신고 누적 횟수

    @Column(nullable = false, length = 4000)
    private String postContent;                     // 본문

    // ✴️N:1(회원 한 명당 여러 개의 게시글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num_fk")
    private UserTb user;

    // ✴️1:N(게시글 한 개당 여러 개의 핀)
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PinTb> pins;
}
