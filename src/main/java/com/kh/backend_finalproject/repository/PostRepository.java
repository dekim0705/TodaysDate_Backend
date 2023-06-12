package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PostRepository extends JpaRepository<PostTb, Long> {
    // ✅메인 페이지 : 전체 지역 게시글 작성일 최근순 정렬
    @Query("SELECT new com.kh.backend_finalproject.dto.PostUserDto(u.pfImg, u.nickname, p.title, p.district, " +
            "p.imgUrl, p.writeDate) " +
            "FROM UserTb u INNER JOIN u.posts p " +
            "ORDER BY p.writeDate DESC")
    List<PostUserDto> getAllPostsWithUserDetails();
}
