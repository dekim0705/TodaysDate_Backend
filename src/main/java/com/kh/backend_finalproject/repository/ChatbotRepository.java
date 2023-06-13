package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<ChatbotTb, Long> {
//    List<ChatbotTb> findAll();

    // 💖관리자 페이지: 전체 문의 내역 조회 (문의일 최근순 정렬)
    @Query("SELECT new com.kh.backend_finalproject.dto.ChatbotUserDto(u.nickname, i.inquiryNum, i.inquiryContent, i.inquiryDate," +
            " i.email, i.inquiryStatus) " +
            "FROM ChatbotTb i " +
            "INNER JOIN i.user u " +
            "ORDER BY i.inquiryDate DESC")
    List<ChatbotUserDto> findAllInquiryWithUserNickname();
}
