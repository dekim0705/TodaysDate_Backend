package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.ChatbotUserDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.ReplyTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyTb, Long> {
    // 💖관리자 페이지: 전체 댓글 조회 (최근순 정렬)
    @Query("SELECT new com.kh.backend_finalproject.dto.ReplyUserDto(u.nickname, r.id, r.content, r.writeDate, u.id) " +
            "FROM ReplyTb r " +
            "INNER JOIN r.user u " +
            "ORDER BY r.writeDate DESC")
    List<ReplyUserDto> findAllReplyWithUserNickname();

    @Query("SELECT new com.kh.backend_finalproject.dto.ReplyUserDto(u.nickname, r.id, r.content, r.writeDate, u.id) " +
            "FROM ReplyTb r " +
            "JOIN r.user u " +
            "WHERE r.content LIKE %:keyword% OR u.nickname LIKE %:keyword%")
    List<ReplyUserDto> searchByKeyword(String keyword);
}



