package com.kh.backend_finalproject.entitiy;
import com.kh.backend_finalproject.constant.IsActive;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.constant.RegionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter @ToString
public class UserTb {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_num")
    private Long id;                                // 회원 번호

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
    private RegionStatus userRegion;                // 관심 지역

    @Column(nullable = false)
    private LocalDateTime regDate;                  // 가입일

    @Enumerated(EnumType.STRING)
    private IsPush ispush;                          // 알림 수신 여부

    @Enumerated(EnumType.STRING)
    private IsMembership isMembership;              // 멤버십 여부

    @Column(length = 10)
    private String authKey;                         // 이메일 인증키

    @Enumerated(EnumType.STRING)
    private IsActive isActive;                      // 이메일 인증 여부

    /* 🦄양방향 쓴 이유: 게시글의 작성자만 수정/삭제 가능하게 하기 위해
                      상세페이지에서 작성자 닉네임 노출하기 위해
       ✅PostTb와 1:N 관계이므로 UserTb에는 @OneToMany 사용!
         헷갈리면 PostTb 클래스 확인! */
    @OneToMany(mappedBy = "user")
    private List<PostTb> posts;

    @OneToMany(mappedBy = "user")
    private List<FolderTb> folders;

    @OneToMany(mappedBy = "user")
    private List<ReplyTb> replies;

    @OneToMany(mappedBy = "blocker")
    private List<BlockTb> blockedUsers;

    @OneToMany(mappedBy = "blocked")
    private List<BlockTb> blockUsers;

    @OneToMany(mappedBy = "reporter")
    private List<ReportTb> reportedUsers;

    @OneToMany(mappedBy = "reported")
    private List<ReportTb> reportUsers;
}
