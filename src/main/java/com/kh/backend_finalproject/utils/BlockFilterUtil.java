package com.kh.backend_finalproject.utils;

import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.repository.BlockRepository;

import java.util.List;
import java.util.stream.Collectors;

public class BlockFilterUtil {

    private BlockFilterUtil() {} // 외부에서 객체 생성 방지하기 위해 작성

    // ♻️blockerId가 차단한 사용자들의 목록 가져오기
    public static List<Long> getBlockedUserIds(Long blockerId, BlockRepository blockRepository) {
        // 1. 차단한 사용자 목록 가져오기
        List<BlockTb> blockedUsers = blockRepository.findByBlockerId(blockerId);

        // 2. 차단한 사용자들의 userNum(Id) 만들기
        List<Long> blockedUserIds = blockedUsers.stream()
                .map(blockTb -> blockTb.getBlocked().getId())
                .collect(Collectors.toList());

        return blockedUserIds;
    }
}
