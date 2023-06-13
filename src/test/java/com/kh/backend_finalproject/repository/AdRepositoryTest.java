package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.dto.AdDto;
import com.kh.backend_finalproject.dto.ReplyUserDto;
import com.kh.backend_finalproject.entitiy.AdTb;
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

public class AdRepositoryTest {
    @Autowired
    AdRepository adRepository;

    @Test
    @DisplayName("광고 추가 테스트")
    public void createAdTest() {
        for(int i = 1; i <= 3; i++) {
            AdTb ad = new AdTb();
            ad.setId((long) i);
            ad.setName("광고이름 " + i);
            ad.setImgUrl("광고 url" + i);

            adRepository.save(ad);
        }
    }

    @Test
    @DisplayName("광고 조회 테스트")
    public void findAllReplyWithUserNicknameTest() {
        List<AdTb> ads = adRepository.findAll();
        for (AdTb e : ads) {
            System.out.println("💗광고 번호 : " + e.getId());
            System.out.println("💗광고이름 : " + e.getName());
            System.out.println("💗이미지url : " + e.getImgUrl());
            System.out.println("===============================================================");


        }
    }
}
