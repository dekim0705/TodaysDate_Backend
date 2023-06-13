package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.entitiy.ReplyTb;
import com.kh.backend_finalproject.entitiy.ReportTb;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
@Transactional

public class ReportRepositoryTest {
    @Autowired
    ReportRepository reportRepository;

    @Test
    @DisplayName("신고내역 조회 테스트")
    public void findAllReportTest() {
        List<ReportTb> reports = reportRepository.findAll();
        for (ReportTb e : reports) {
            System.out.println("💗신고 번호 : " + e.getId());
            System.out.println("💗신고내용 : " + e.getContent());
            System.out.println("💗신고자 : " + e.getReporter());
            System.out.println("💗신고일 : " + e.getReportDate());
            System.out.println("===============================================================");


        }
    }

}
