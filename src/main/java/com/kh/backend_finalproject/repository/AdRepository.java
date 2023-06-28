package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.AdTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdRepository extends JpaRepository<AdTb, Long> {
    List<AdTb> findAllByOrderByIdDesc();
}
