package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.repository.BlockRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReportRepository;
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

    // 사용자 차단하기

    // 사용자 신고하기
}
