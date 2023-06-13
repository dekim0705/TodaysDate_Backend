package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.dto.ChatbotDto;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import com.kh.backend_finalproject.repository.ChatbotRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChatbotService {
    private final ChatbotRepository chatbotRepository;


}
