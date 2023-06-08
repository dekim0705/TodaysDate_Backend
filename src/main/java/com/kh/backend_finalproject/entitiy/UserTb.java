package com.kh.backend_finalproject.entitiy;
import com.kh.backend_finalproject.constant.CityStatus;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class UserTb {
    @Id
    @Column(name = "user_num_pk")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userNum;                            // 회원 번호

    @Column(nullable = false, unique = true, length = 50)
    private String email;                           // 이메일

    @Column(nullable = false, length = 20)
    private String pwd;                             // 비밀번호

    @Column(unique = true, length = 30)
    private String nickname;                        // 닉네임

    @Column(columnDefinition = "varchar(80) default '안녕하세요. 더 많은 경로를 알고싶습니다.'")
    private String userComment;                     // 한 줄 소개

    @Column(columnDefinition = "varchar(500) default '기본이미지 들어갈 예정'")
    private String pfImg;                           // 프로필 사진

    @Enumerated(EnumType.STRING)
    private CityStatus userCity;                    // 관심 지역

    @Column(nullable = false)
    private LocalDateTime regDate;                  // 가입일

    @Column(columnDefinition = "char(1) default 'N'")
    private char isPushOk;                          // 알림 수신 여부

    @Column(columnDefinition = "char(1) default 'N'")
    private char isMembership;                      // 멤버십 여부

    @Column(length = 10)
    private String authKey;                         // 이메일 인증키

    @Column(columnDefinition = "char(1) default 'N'")
    private char isActive;                          // 이메일 인증 여부

    // ✴️1:N(회원 한 명당 여러 개의 게시글)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostTb> posts;

    // ✴️1:N(회원 한 명당 여러 개의 북마크)
    @OneToMany(mappedBy = "user")
    private List<BookmarkTb> bookmarks;
}
