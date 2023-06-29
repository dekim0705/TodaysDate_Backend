package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.dto.ReportRequestDto;
import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.ReportTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.BlockRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReportRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;
    private final TokenProvider tokenProvider;
    private final AuthService authService;


    // 🔐게시글 신고하기 (SecurityContext 적용 OK)
    public void reportPost(Long postId, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));

        if(user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("본인의 게시글은 신고할 수 없습니다. 😅");
        } else {
            post.setReportCount(post.getReportCount()+1);
            postRepository.save(post);
            if(post.getReportCount() == 3) {
                postRepository.delete(post);
            }
        }
    }

    // 🔐사용자 차단하기 (SecurityContext 적용 OK)
    public void blockUser(Long blockedId, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 사용자가 존재하는지 확인
        UserTb blocker = userRepository.findById(user.getId()) // 차단하려는 사용자
                .orElseThrow(() -> new IllegalArgumentException("차단하려는 사용자가 존재하지 않습니다." + user.getId()));
        UserTb blocked = userRepository.findById(blockedId)
                .orElseThrow(() -> new IllegalArgumentException("차단 당하는 사용자가 존재하지 않습니다." + blockedId));

        // 2. 해당 사용자가 동일 사용자인지 확인
        if(blocker.equals(blocked)) {
            throw new IllegalArgumentException("사용자는 본인을 차단할 수 없습니다. 🙂");
        }

        // 3. blockedId가 blockerId에게 이미 차단되어 있는지 확인
        boolean alreadeyBlocked = blockRepository.findByBlockerAndBlocked(blocker, blocked).isPresent();
        if(alreadeyBlocked) {
            throw new IllegalArgumentException("이미 차단한 사용자 입니다.🙂");
        }

        // 4. 차단하기!!
        BlockTb block = new BlockTb();
        block.setBlocker(blocker);
        block.setBlocked(blocked);
        blockRepository.save(block);
    }

    // 🔐사용자 신고하기 (SecurityContext 적용 OK)
    public void reportUser(ReportRequestDto reportRequestDto, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 사용자가 존재하는지 확인
        UserTb reporter = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("신고하려는 사용자가 존재하지 않습니다." + user.getId()));
        UserTb reported = userRepository.findById(reportRequestDto.getReportedId())
                .orElseThrow(() -> new IllegalArgumentException("신고 당하는 사용자가 존재하지 않습니다." + reportRequestDto.getReportedId()));

        // 2. 신고자와 신고 당하는 사용자가 동일한 사용자인지 확인
        if(reporter.equals(reported)) {
            throw new IllegalArgumentException("사용자는 본인을 신고할 수 없습니다.");
        }

        // 3. 신고하기!!
        ReportTb report = new ReportTb();
        report.setReporter(reporter);
        report.setReported(reported);
        report.setContent(reportRequestDto.getContent());
        report.setReportDate(reportRequestDto.getReportDate());
        reportRepository.save(report);
    }

    // 🔐사용자 차단 해제하기 (SecurityContext 적용 OK)
    public void deleteBlockUser(Long blockedId, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        BlockTb block = blockRepository.findByBlockerIdAndBlockedId(user.getId(), blockedId)
                .orElseThrow(() -> new IllegalArgumentException("차단한 회원이 아닙니다."));

        blockRepository.delete(block);
    }
}
