package com.kh.backend_finalproject.entitiy;

import com.kh.backend_finalproject.constant.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_num")
    private Long id;                                 // 회원 번호

    @Column(nullable = false, unique = true, length = 50)
    private String email;                           // 이메일

    @Column(nullable = false)
    private String pwd;                             // 비밀번호

    @Column(unique = true, length = 30)
    private String nickname;                        // 닉네임

    @Column(length = 80)
    private String userComment;                     // 한 줄 소개

    @Column(length = 500)
    private String pfImg;                           // 프로필 사진

    @Enumerated(EnumType.STRING)
    private RegionStatus userRegion;                // 관심 지역

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime regDate;                  // 가입일

    @Enumerated(EnumType.STRING)
    private IsPush isPush;                          // 알림 수신 여부

    @Enumerated(EnumType.STRING)
    private IsMembership isMembership;              // 멤버십 여부

    @Column(length = 10)
    private String authKey;                         // 이메일 인증키

    @Enumerated(EnumType.STRING)
    private IsActive isActive;                      // 이메일 인증 여부

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public UserTb(String email, String pwd, String nickname, RegionStatus userRegion,
                  IsPush isPush, IsMembership isMembership, Authority authority, String authKey,
                  IsActive isActive, String pfImg, String userComment) {
        this.email = email;
        this.pwd = pwd;
        this.nickname = nickname;
        this.userRegion = userRegion;
        this.isPush = isPush;
        this.isMembership = isMembership;
        this.authority = authority;
        this.authKey = authKey;
        this.isActive = isActive;
        this.pfImg = pfImg;
        this.userComment = userComment;
    }

    /* 🦄양방향 쓴 이유: 게시글의 작성자만 수정/삭제 가능하게 하기 위해
                      상세페이지에서 작성자 닉네임 노출하기 위해
       ✅PostTb와 1:N 관계이므로 UserTb에는 @OneToMany 사용!
         헷갈리면 PostTb 클래스 확인! */
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PostTb> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FolderTb> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReplyTb> replies = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PushTb> push = new ArrayList<>();

    @OneToMany(mappedBy = "blocker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlockTb> blockedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "blocked", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BlockTb> blockUsers = new ArrayList<>();

    @OneToMany(mappedBy = "reporter", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReportTb> reportedUsers = new ArrayList<>();

    @OneToMany(mappedBy = "reported", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ReportTb> reportUsers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ChatbotTb> chatbots = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookmarkTb> bookmarks = new ArrayList<>();
}
