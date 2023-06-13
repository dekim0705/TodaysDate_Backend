package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class ReplyRepositoryTest {
    @Autowired
    ReplyRepository replyRepository;

    @Test
    @DisplayName("문의내역 조회 테스트")
    public void findAllReplyWithUserNicknameTest() {
        List<ReplyUserDto> replyUserDtos = replyRepository.findAllReplyWithUserNickname();
        for (ReplyUserDto e : replyUserDtos) {
            System.out.println("💗댓글 번호 : " + e.getReplyNum());
            System.out.println("💗댓글 내용 : " + e.getContent());
            System.out.println("💗닉네임 : " + e.getNickname());
            System.out.println("💗작성일 : " + e.getWriteDate());
            System.out.println("===============================================================");


        }
    }
}