package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.ChatbotRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ChatbotRepository chatbotRepository ;

    public List<ChatbotUserDto> findAllInquiryList() {
        List<ChatbotUserDto> chatbotUserDtos = chatbotRepository.findAllInquiryWithUserNickname();
        return chatbotUserDtos;
    }

}
