package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostTb, Long> {
}
