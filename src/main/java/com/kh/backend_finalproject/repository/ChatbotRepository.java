package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.entitiy.ChatbotTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotRepository extends JpaRepository<ChatbotTb,Long> {
}
