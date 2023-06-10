package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<PostTb, Long> {
    List<PostTb> findbyRegion(RegionStatus region);
    List<PostTb> findbyTitleOrDistrictOrPlaceTagOrContentContaining(String keyword);
}
