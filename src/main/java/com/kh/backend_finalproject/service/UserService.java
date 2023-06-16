package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.constant.IsMembership;
import com.kh.backend_finalproject.constant.IsPush;
import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.*;
import com.kh.backend_finalproject.dto.UserProfileDto;
import com.kh.backend_finalproject.repository.FolderRepository;
import com.kh.backend_finalproject.repository.PostRepository;
import com.kh.backend_finalproject.repository.ReplyRepository;
import com.kh.backend_finalproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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
    private final FolderRepository folderRepository;

    // ✅ 마이페이지 - 회원 프로필 바 가져오기 (프로필사진, 닉네임, 멤버십 여부, 한 줄 소개, 총 게시글/댓글 수)
    public List<UserProfileDto> getUserProfileInfo(String email) {
        List<UserProfileDto> profileDtos = userRepository.findUserProfileInfo(email);
        return profileDtos;
    }
    // ✅ 마이페이지 - 회원의 모든 게시글 가져오기 (글 번호, 제목, 본문, 조회수, 작성일, 작성자 닉네임)
    public List<UserDto> getAllUserPosts(String email) {
        Optional<UserTb> user = userRepository.findByEmail(email);
        List<PostTb> posts = user.get().getPosts();;
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
        Optional<UserTb> user = userRepository.findByEmail(email);
        List<ReplyTb> replies = user.get().getReplies();
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
        Optional<UserTb> user = userRepository.findByEmail(email);
        return user.get().getIsMembership();
    }
    // ✅ 마이페이지 - 회원의 푸쉬알림 상태 조회
    public IsPush getUserNotificationStatus(String email) {
        Optional<UserTb> user = userRepository.findByEmail(email);
        return user.get().getIsPush();
    }

    // ✅ 마이페이지 - 회원의 푸쉬알림 상태 변경
    public IsPush updateUserNotificationStatus(String email) {
        Optional<UserTb> user = userRepository.findByEmail(email);
        IsPush currentStatus = user.get().getIsPush();
        System.out.println("🍒(" + email + ")현재 알림 설정 상태  : " + currentStatus);

        IsPush newStatus = currentStatus.equals(IsPush.PUSH) ? IsPush.NOPUSH : IsPush.PUSH;
        user.get().setIsPush(newStatus);
        userRepository.save(user.get());
        System.out.println("🍒(" + email + ")변경된 알림 설정 : " + newStatus);

        return newStatus;
    }

    // ✅ 마이페이지 - 회원의 북마크 폴더 가져오기
    public List<FolderDto> getUserBookmarkFolders(String email) {
        Optional<UserTb> user = userRepository.findByEmail(email);
        if (user != null) {
            List<FolderDto> folderDtos = new ArrayList<>();
            for (FolderTb folder : user.get().getFolders()) {
                FolderDto folderDto = new FolderDto();
                folderDto.setId(folder.getId());
                folderDto.setName(folder.getName());

//                List<BookmarkDto> bookmarkDtos = new ArrayList<>();
//                for (BookmarkTb bookmark : folder.getBookmarks()) {
//                    BookmarkDto bookmarkDto = new BookmarkDto();
//                    bookmarkDto.setId(bookmark.getId());
//                    bookmarkDto.setPostId(bookmark.getPost().getId());
//
//                    bookmarkDtos.add(bookmarkDto);
//                }
//                folderDto.setBookmarks(bookmarkDtos);

                folderDtos.add(folderDto);
            }
            return folderDtos;
        }
        return Collections.emptyList();
    }

    // ✅ 마이페이지 - 회원의 북마크 가져오기
    public List<BookmarkDto> getBookmarksInFolder(Long folderId, String email) {
        Optional<FolderTb> folderOptional = folderRepository.findById(folderId);
        if (folderOptional.isPresent()) {
            FolderTb folder = folderOptional.get();
            // 폴더 소유자 확인
            if (folder.getUser().getEmail().equals(email)) {
                List<BookmarkDto> bookmarkDtos = new ArrayList<>();
                for (BookmarkTb bookmark : folder.getBookmarks()) {
                    BookmarkDto bookmarkDto = new BookmarkDto();
                    bookmarkDto.setId(bookmark.getId());
                    bookmarkDto.setPostId(bookmark.getPost().getId());
                    bookmarkDto.setImgUrl(bookmark.getPost().getImgUrl());
                    bookmarkDto.setTitle(bookmark.getPost().getTitle());
                    bookmarkDto.setDistrict(bookmark.getPost().getDistrict());

                    bookmarkDtos.add(bookmarkDto);
                }
                return bookmarkDtos;
            } else {
                throw new IllegalArgumentException("해당 폴더에 접근 권한이 없습니다.");
            }
        }
        return Collections.emptyList();
    }

    // ✅ 마이페이지 - 회원정보 수정
    public boolean updateInformation(Long userId, UserDto userDto) throws IllegalAccessException {
        UserTb user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalAccessException("해당 회원이 없습니다." + userId));

        if (userDto.getPfImg() == null || userDto.getPfImg().isEmpty()
                || userDto.getNickname() == null || userDto.getNickname().isEmpty()
                || userDto.getUserComment() == null || userDto.getUserComment().isEmpty()
                || userDto.getUserRegion() == null) {
            throw new IllegalArgumentException("모든 정보를 입력해 주세요..😰");
        }

        user.setPfImg(userDto.getPfImg());
        user.setNickname(userDto.getNickname());
        user.setUserComment(userDto.getUserComment());
        user.setUserRegion(userDto.getUserRegion());
        userRepository.save(user);

        return true;
    }

    // ✅ 마이페이지 - 비밀번호 변경
    public boolean updatePwd(Long userId, UserTb userTb) throws IllegalAccessException {
        UserTb user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다." + userId));

        if (userTb.getPwd() == null || userTb.getPwd().isEmpty()) {
            throw new IllegalArgumentException("비밀번호가 없어요..😰");
        }

        user.setPwd(userTb.getPwd());
        UserTb savedUser = userRepository.save(user);
        log.info(savedUser.toString());

        return true;
    }

    // ✅ 마이페이지 - 회원 탈퇴
    public void deleteUser(Long userId) throws IllegalAccessException{
        UserTb userTb = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        userRepository.delete(userTb);
    }

    // ✅ 회원가입 - 닉네임 중복 확인
    public Optional<UserTb> findUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname);
    }

    // ✅ 회원가입 - 이메일 중복 확인
    public boolean findUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
