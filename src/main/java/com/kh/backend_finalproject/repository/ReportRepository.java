package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.ReportTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<ReportTb, Long> {
    List<ReportTb> findAllByOrderByIdDesc();
}
