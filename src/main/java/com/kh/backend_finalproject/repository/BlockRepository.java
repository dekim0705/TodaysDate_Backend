package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.BlockTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<BlockTb, Long> {
}
