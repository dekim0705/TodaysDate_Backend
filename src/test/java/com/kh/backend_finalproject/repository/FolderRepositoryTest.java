package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.FolderTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Sql("/dummy_test.sql")
class FolderRepositoryTest {
    @Autowired
    FolderRepository folderRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("폴더 유무 확인 테스트")
    public void findByNameAndUserTest() {
        Long userId = 2L;
        Optional<UserTb> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            UserTb user = userOptional.get();
            Optional<FolderTb> folder = folderRepository.findByNameAndUser("북마크1", user);
            System.out.println("🦄 : " +  folder.isEmpty());
        } else System.out.println("🦄 : 사용자를 찾을 수 없습니다.");
    }
}