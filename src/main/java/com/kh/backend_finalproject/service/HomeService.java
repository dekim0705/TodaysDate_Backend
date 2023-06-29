package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.dto.UserDto;
import com.kh.backend_finalproject.entitiy.*;
import com.kh.backend_finalproject.jwt.TokenProvider;
import com.kh.backend_finalproject.repository.*;
import com.kh.backend_finalproject.utils.BlockFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class HomeService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final FolderRepository folderRepository;
    private final AdRepository adRepository;
    private final BlockRepository blockRepository;
    private final TokenProvider tokenProvider;
    private final AuthService authService;

    // 🌴🔐️특정 사용자가 차단한 사용자 여부와 함께 전체 지역 게시글 작성일 최근순 정렬 (SecurityContext 적용 OK)
    public List<PostUserDto> findAllPostsList(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(user.getId(), blockRepository);

        // 2. 전체 게시글 가져오기
        List<PostUserDto> allPosts = postRepository.findAllPostsWithUserDetails();

        // 3. 차단한 사용자가 작성한 게시물 여부 확인
        for (PostUserDto post : allPosts) {
            if (blockedUserIds.contains(post.getId())) {
                post.setBlocked(true);
            } else {
                post.setBlocked(false);
            }
        }
        return allPosts;
    }

    // 🌴🔐특정 사용자가 차단한 사용자의 게시글 제외 특정 지역 게시글 작성일 최근순 정렬 (SecurityContext 적용 OK)
    public List<PostUserDto> findRegionPostsList(RegionStatus status, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(user.getId(), blockRepository);

        // 2. 특정 지역 게시글 가져오기
        List<PostUserDto> regionPosts = postRepository.findRegionPostsWithUserDetails(status);

        // 3. 차단한 사용자가 작성한 게시물 여부 확인
        for (PostUserDto post : regionPosts) {
            if (blockedUserIds.contains(post.getId())) {
                post.setBlocked(true);
            } else {
                post.setBlocked(false);
            }
        }
        return regionPosts;
    }

    // 🌴🔐키워드 검색 (SecurityContext 적용 OK)
    public List<PostUserDto> findByKeyword(String keyword, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(user.getId(), blockRepository);

        // 2. 키워드로 검색한 게시글 가져오기
        List<PostTb> postList = postRepository.findByKeyword(keyword);

        // 3. 차단한 사용자가 작성한 게시글 제외 및 isBlocked 설정
        List<PostUserDto> postUserDtos = new ArrayList<>();
        for (PostTb e : postList) {
            PostUserDto postUserDto = new PostUserDto();
            postUserDto.setPostId(e.getId());
            postUserDto.setPfImg(e.getUser().getPfImg());
            postUserDto.setNickname(e.getUser().getNickname());
            postUserDto.setWriteDate(e.getWriteDate());
            postUserDto.setTitle(e.getTitle());
            postUserDto.setDistrict(e.getDistrict());
            postUserDto.setThumbnail(e.getImgUrl());
            if (blockedUserIds.contains(e.getUser().getId())) {
                postUserDto.setBlocked(true);
            } else {
                postUserDto.setBlocked(false);
            }
            postUserDtos.add(postUserDto);
        }
        return postUserDtos;
    }

    // 🔐북마크 상위 5개 게시글 내림차순 정렬 (SecurityContext 적용 OK)
    public Page<PostBookmarkDto> findTop5ByBookmarkCount(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb user = authService.validateTokenAndGetUser(request, userDetails);

        Pageable topFive = PageRequest.of(0, 5);
        Page<PostBookmarkDto> postBookmarkDtos = postRepository.findTop5ByBookmarkCount(topFive);
        return postBookmarkDtos;
    }

    // 🔐회원 정보 가져오기 (SecurityContext 적용 OK)
    public UserDto findPfImgById(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb authUser = authService.validateTokenAndGetUser(request, userDetails);

        Optional<UserTb> user = userRepository.findById(authUser.getId());
        UserDto userDto = new UserDto();
        userDto.setPfImg(user.get().getPfImg());
        userDto.setIsMembership(user.get().getIsMembership());
        userDto.setId(user.get().getId());
        userDto.setNickname(user.get().getNickname());
        userDto.setUserComment(user.get().getUserComment());
        userDto.setPostCount(user.get().getPosts().size());
        userDto.setReplyCount(user.get().getReplies().size());
        userDto.setUserRegion(user.get().getUserRegion());

        return userDto;
    }

    // 🔐북마크 추가 (SecurityContext 적용 OK)
    public boolean createBookmark(Long postId, String folderName, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb authUser = authService.validateTokenAndGetUser(request, userDetails);

        Optional<UserTb> userOptional = userRepository.findById(authUser.getId());
        Optional<PostTb> postOptional = postRepository.findById(postId);
        if (userOptional.isEmpty() || postOptional.isEmpty()) return false;

        UserTb user = userOptional.get();
        PostTb post = postOptional.get();

        FolderTb folder = folderRepository.findByNameAndUser(folderName, user)
                .orElseGet(() -> {
                    FolderTb newFolder = new FolderTb();
                    newFolder.setName(folderName);
                    newFolder.setUser(user);
                    return folderRepository.save(newFolder);
                });

        BookmarkTb bookmark = new BookmarkTb();
        bookmark.setFolder(folder);
        bookmark.setUser(user);
        bookmark.setPost(post);
        bookmarkRepository.save(bookmark);

        return true;
    }

    // 🔐북마크 삭제 (SecurityContext 적용 OK)
    public boolean deleteBookmark(Long postId, String folderName, HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb authUser = authService.validateTokenAndGetUser(request, userDetails);

        Optional<UserTb> userOptional = userRepository.findById(authUser.getId());
        Optional<PostTb> postOptional = postRepository.findById(postId);
        if (userOptional.isEmpty() || postOptional.isEmpty()) return false;

        UserTb user = userOptional.get();
        PostTb post = postOptional.get();

        FolderTb folder = folderRepository.findByNameAndUser(folderName, user)
                .orElse(null);
        if (folder == null) return false;

        BookmarkTb bookmark = bookmarkRepository.findByFolderAndPost(folder, post)
                .orElse(null);
        if (bookmark == null) return false;

        bookmarkRepository.delete(bookmark);

        return true;
    }

    // 🔐광고 전체 가져오기 (SecurityContext 적용 OK)
    public List<AdTb> findAllAd(HttpServletRequest request, UserDetails userDetails) {
        // 🔑토큰 검증 및 사용자 정보 추출
        UserTb authUser = authService.validateTokenAndGetUser(request, userDetails);

        List<AdTb> ads = adRepository.findAll();
        return ads;
    }
}
