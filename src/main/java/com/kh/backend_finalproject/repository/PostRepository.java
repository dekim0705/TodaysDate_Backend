package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<PostTb, Long> {
    // ✅메인 페이지 : 전체 지역 게시글 작성일 최근순 정렬
    @Query(value =
            "SELECT u.pf_img, u.nickname, p.title, p.district, p.img_url, p.write_date " +
                    "FROM user_tb u " +
                    "INNER JOIN post_tb p " +
                    "ON u.user_num = p.user_num " +
                    "ORDER BY p.write_date DESC", nativeQuery = true)
    List<Object[]> findAllPostsWithUserDetails();

    // ✅메인 페이지 : 특정 지역 게시글 작성일 최근순 정렬
    @Query(value =
            "SELECT u.pf_img, u.nickname, p.title, p.district, p.img_url, p.write_date " +
                    "FROM user_tb u " +
                    "INNER JOIN post_tb p " +
                    "ON u.user_num = p.user_num " +
                    "WHERE p.region = :region " +
                    "ORDER BY p.write_date DESC", nativeQuery = true)
    List<Object[]> findAllPostsWithUserDetails(@Param("region") String region);

    // ✅메인 페이지 : 키워드 검색
    @Query("SELECT p FROM PostTb p WHERE p.title LIKE %:keyword% OR p.district LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<PostTb> findByKeyword(@Param("keyword") String keyword);

    // ✅메인 페이지 : 북마크 상위 5개 게시글 내림차순 정렬(북마크 수, 제목)
    @Query(value = "SELECT p.title, COUNT(b.post_id) as bookmark_count " +
            "FROM post_tb p " +
            "JOIN bookmark_tb b ON p.post_num = b.post_id " +
            "GROUP BY p.post_num, p.title " +
            "ORDER BY bookmark_count DESC " +
            "LIMIT 5", nativeQuery = true)
    List<Object[]> findTop5ByBookmarkCount();
}
