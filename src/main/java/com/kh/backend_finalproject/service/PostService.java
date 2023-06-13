package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.repository.PinRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PinRepository pinRepository;

    // 글 작성
    public boolean createPostWithPinAndPush() {


    }
}
