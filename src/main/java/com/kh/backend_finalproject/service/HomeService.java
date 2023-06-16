package com.kh.backend_finalproject.service;

import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.*;
import com.kh.backend_finalproject.repository.*;
import com.kh.backend_finalproject.utils.BlockFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 🔐️특정 사용자가 차단한 사용자의 게시글 제외 전체 지역 게시글 작성일 최근순 정렬
    public List<PostUserDto> findAllPostsList(Long blockerId) {
        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(blockerId, blockRepository);

        // 2. 전체 게시글 가져오기
        List<PostUserDto> allPosts = postRepository.findAllPostsWithUserDetails();

        // 3. 차단한 사용자가 작성한 게시물 제외
        List<PostUserDto> filterPosts = allPosts.stream()
                .filter(postUserDto -> !blockedUserIds.contains(postUserDto.getId()))
                .collect(Collectors.toList());

        return filterPosts;
    }

    // 🔐특정 사용자가 차단한 사용자의 게시글 제외 특정 지역 게시글 작성일 최근순 정렬
    public List<PostUserDto> findRegionPostsList(RegionStatus status, Long blockerId) {
        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(blockerId, blockRepository);

        // 2. 특정 지역 게시글 가져오기
        List<PostUserDto> regionPosts = postRepository.findRegionPostsWithUserDetails(status);

        // 3. 차단한 사용자가 작성한 게시물 제외
        List<PostUserDto> filterPosts = regionPosts.stream()
                .filter(postUserDto -> !blockedUserIds.contains(postUserDto.getId()))
                .collect(Collectors.toList());

        return filterPosts;
    }

    // 🔐🚧키워드 검색
    public List<PostUserDto> findByKeyword(Long blockerId, String keyword) {
        // 1. 차단한 사용자들의 목록 가져오기
        List<Long> blockedUserIds = BlockFilterUtil.getBlockedUserIds(blockerId, blockRepository);

        // 2. 키워드로 검색한 게시글 가져오기
        List<PostTb> postList = postRepository.findByKeyword(keyword);

        // 3. 차단한 사용자가 작성한 게시글 제외
        List<PostUserDto> postUserDtos = new ArrayList<>();
        for (PostTb e : postList) {
            if (blockedUserIds.contains(e.getUser().getId())) {
                continue;
            }
            PostUserDto postUserDto = new PostUserDto();
            postUserDto.setPfImg(e.getUser().getPfImg());
            postUserDto.setNickname(e.getUser().getNickname());
            postUserDto.setWriteDate(e.getWriteDate());
            postUserDto.setTitle(e.getTitle());
            postUserDto.setDistrict(e.getDistrict());
            postUserDto.setThumbnail(e.getImgUrl());
            postUserDtos.add(postUserDto);
        }
        return postUserDtos;
    }

    // ✅북마크 상위 5개 게시글 내림차순 정렬
    public Page<PostBookmarkDto> findTop5ByBookmarkCount() {
        Pageable topFive = PageRequest.of(0, 5);
        Page<PostBookmarkDto> postBookmarkDtos = postRepository.findTop5ByBookmarkCount(topFive);
        return postBookmarkDtos;
    }

    // 🔐회원 프로필 가져오기(by Email)
    public String findPfImgByEmail(String email) {
        UserTb user = userRepository.findByEmail(email);
        return user.getPfImg();
    }

    // 🔐북마크 추가
    public boolean createBookmark(Long userId, Long postId, String folderName) {
        Optional<UserTb> userOptional = userRepository.findById(userId);
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
        bookmark.setPost(post);
        bookmarkRepository.save(bookmark);

        return true;
    }

    // ✅광고 전체 가져오기
    public List<AdTb> findAllAd() {
        List<AdTb> ads = adRepository.findAll();
        return ads;
    }
}
