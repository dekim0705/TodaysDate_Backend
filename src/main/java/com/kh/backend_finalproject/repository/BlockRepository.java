package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockTb, Long> {
    Optional<BlockTb> findByBlockerAndBlocked(UserTb blocker, UserTb blocked);
    List<BlockTb> findByBlockerId(Long blockerId);
    Optional<BlockTb> findByBlockerIdAndBlockedId(Long blockerId, Long blockedId);
}
