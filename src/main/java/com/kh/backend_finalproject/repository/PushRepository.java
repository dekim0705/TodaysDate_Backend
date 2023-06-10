package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.PushTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PushRepository extends JpaRepository<PushTb, Long> {
}
