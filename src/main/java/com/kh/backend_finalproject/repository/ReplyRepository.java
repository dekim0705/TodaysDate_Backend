package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.ReplyTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReplyRepository extends JpaRepository<ReplyTb, Long> {
    List<ReplyTb> findAllByOrderByIdDesc();

    //💗 관리자 페이지 : 댓글 검색 (댓글내용, 작성자 검색)
    @Query("SELECT r FROM ReplyTb r JOIN r.user u WHERE r.content LIKE %:keyword% OR u.nickname LIKE %:keyword%")
    List<ReplyTb> findByKeywordReply(@Param("keyword") String keyword);
}



