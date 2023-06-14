package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.dto.UserDto;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    public List<UserProfileDto> getUserProfileInfo(String email) {
        List<UserProfileDto> profileDtos = userRepository.findUserProfileInfo(email);
        return profileDtos;
    }
    // 마이페이지 - 회원과 매핑된 모든 정보 가져오기
    public UserTb getUserInfo(Long userId) throws IllegalAccessException {
        UserTb user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalAccessException("해당 사용자가 없습니다." + userId));
        return user;
    }

}
