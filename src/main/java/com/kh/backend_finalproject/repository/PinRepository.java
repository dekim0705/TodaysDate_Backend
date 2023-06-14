package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.PinTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<PinTb, Long> {
    void deleteAllByPost(PostTb post);
}
