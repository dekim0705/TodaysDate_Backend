package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.BlockTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<BlockTb, Long> {
    Optional<BlockTb> findByBlockerAndBlocked(UserTb blocker, UserTb blocked);
}
