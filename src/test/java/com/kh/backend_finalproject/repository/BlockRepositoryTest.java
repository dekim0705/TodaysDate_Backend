package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class BlockRepositoryTest {
    @Autowired
    UserRepository userRepository;
    @Autowired
    BlockRepository blockRepository;

    @Test
    @DisplayName("차단 여부 확인 테스트")
    public void findByBlockerAndBlockedTest() {
        UserTb block = userRepository.findByEmail("user1@naver.com");
        UserTb blocked = userRepository.findByEmail("user2@kakao.com");
        Optional<BlockTb> isBlocked = blockRepository.findByBlockerAndBlocked(block, blocked);
        System.out.println("🦄 1번 회원이 2번 회원을 차단했나요? : " + isBlocked.isPresent());
    }

}