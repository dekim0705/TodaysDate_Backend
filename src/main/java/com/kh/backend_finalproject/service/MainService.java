package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.repository.BookmarkRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class MainService {
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;

}
