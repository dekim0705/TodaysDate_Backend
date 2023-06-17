package com.kh.backend_finalproject.entitiy;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class PostTb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_num")
    private Long id;                                // 게시글 번호

    @Column(nullable = false, length = 500)
    private String title;                           // 제목

    @Enumerated(EnumType.STRING)
    private RegionStatus region;                    // 지역

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

    @Column(length = 1000)
    private String imgUrl;                          // 이미지 주소

    private int viewCount;                          // 조회수

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime writeDate;                // 작성일

    @Column
    private Integer reportCount = 0;                // 신고 누적 횟수

    @Column(nullable = false, length = 4000)
    private String content;                         // 본문

    /* 🦄'한 사람'이 '여러 개의 게시글'을 작성할 수 있으므로 N:1 매핑 설정!!
    * 따라서, 연관 관계의 주인은 N인 PostTb가 됩니다.  */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_num")
    private UserTb user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkTb> bookmarks;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReplyTb> replies;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PinTb> pins;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PushTb> pushes;
}
