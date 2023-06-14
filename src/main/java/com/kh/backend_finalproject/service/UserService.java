package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.entitiy.ReplyTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReplyRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    public List<UserProfileDto> getUserProfileInfo(String email) {
        List<UserProfileDto> profileDtos = userRepository.findUserProfileInfo(email);
        return profileDtos;
    }
    // ✅ 마이페이지 - 회원의 모든 게시글 가져오기 (글 번호, 제목, 본문, 조회수, 작성일, 작성자 닉네임)
    public List<UserDto> getAllUserPosts(String email) {
        UserTb user = userRepository.findByEmail(email);
        List<PostTb> posts = user.getPosts();
        List<UserDto> userDtoList = new ArrayList<>();

        for (PostTb post : posts) {
            UserDto userDto = new UserDto();
            userDto.setPostNum(post.getId());
            userDto.setTitle(post.getTitle());
            userDto.setContent(post.getContent());
            userDto.setViewCount(post.getViewCount());
            userDto.setWriteDate(post.getWriteDate());
            userDto.setNickname(post.getUser().getNickname());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }
    // ✅ 마이페이지 - 회원의 모든 댓글 가져오기 (댓글 번호, 작성자 닉네임, 댓글 본문, 원문 제목, 작성일)
    public List<UserDto> getAllUserReplies(String email) {
        UserTb user = userRepository.findByEmail(email);
        List<ReplyTb> replies = user.getReplies();
        List<UserDto> userDtoList = new ArrayList<>();

        for (ReplyTb reply : replies) {
            UserDto userDto = new UserDto();
            userDto.setReplyNum(reply.getId());
            userDto.setNickname(reply.getUser().getNickname());
            userDto.setContent(reply.getContent());
            userDto.setTitle(reply.getPost().getTitle());
            userDto.setWriteDate(reply.getWriteDate());

            userDtoList.add(userDto);
        }
        return userDtoList;
    }
    // ✅ 마이페이지 - 회원의 멤버십 상태 조회
    public IsMembership getUserMembershipStatus(String email) {
        UserTb user = userRepository.findByEmail(email);
        return user.getIsMembership();
    }
    // ✅ 마이페이지 - 회원의 푸쉬알림 상태 조회
    public IsPush getUserNotificationStatus(String email) {
        UserTb user = userRepository.findByEmail(email);
        return user.getIsPush();
    }
}
