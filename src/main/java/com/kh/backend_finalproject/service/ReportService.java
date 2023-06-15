package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.BlockRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReportRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ReportService {
    private final PostRepository postRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final BlockRepository blockRepository;

    // ✅게시글 신고하기
    public void reportPost(Long postId) {
        PostTb post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        post.setReportCount(post.getReportCount()+1);
        postRepository.save(post);
        if(post.getReportCount() == 3) {
            postRepository.delete(post);
        }
    }

    // ✅사용자 차단하기
    public void blockUser(Long blockerId, Long blockedId) {
        // 1. 사용자가 존재하는지 확인
        UserTb blocker = userRepository.findById(blockerId) // 차단하려는 사용자
                .orElseThrow(() -> new IllegalArgumentException("차단하려는 사용자가 존재하지 않습니다." + blockerId));
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

    // 🔴사용자 신고하기
}
