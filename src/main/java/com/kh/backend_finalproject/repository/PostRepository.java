package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<PostTb, Long> {
    List<PostTb> findByRegion(RegionStatus region);
    @Query("SELECT p FROM PostTb p WHERE p.title LIKE %:keyword% OR p.district LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<PostTb> findByKeyword(@Param("keyword") String keyword);
}
