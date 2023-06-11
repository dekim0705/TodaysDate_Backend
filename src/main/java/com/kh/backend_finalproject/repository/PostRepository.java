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
    @Query(value = "SELECT p.* " +
            "FROM post_tb p " +
            "INNER JOIN ( " +
            "    SELECT post_id " +
            "    FROM bookmark_tb " +
            "    GROUP BY post_id " +
            "    ORDER BY COUNT(*) DESC " +
            "    LIMIT 5 " +
            ") b " +
            "ON p.post_num = b.post_id", nativeQuery = true)
    List<PostTb> findTop5ByBookmarkCount();
}
