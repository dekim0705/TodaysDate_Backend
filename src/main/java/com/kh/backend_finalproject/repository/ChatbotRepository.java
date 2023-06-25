package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<ChatbotTb, Long> {
    List<ChatbotTb> findAllByOrderByInquiryDateDesc();
}