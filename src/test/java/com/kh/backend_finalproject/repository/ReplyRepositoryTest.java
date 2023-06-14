package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.ReplyTb;
import com.kh.backend_finalproject.entitiy.ReportTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;


    @Test
    @Transactional
    @DisplayName("관리자페이지 - 전체 댓글 조회 테스트")
    public void findAllRepliesTest () {
        List<ReplyTb> postTbs = replyRepository.findAll();
        for (ReplyTb e : postTbs) {
            System.out.println("💗댓글번호 : " + e.getId());
            System.out.println("💗댓글내용 : " + e.getContent());
            System.out.println("💗닉네임 : " + e.getUser().getNickname());
            System.out.println("💗작성일 : " + e.getWriteDate());
            System.out.println("—————————————— ");

        }
    }
}
