package com.kh.backend_finalproject.repository;

import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.dto.PostBookmarkDto;
import com.kh.backend_finalproject.dto.PostUserDto;
import com.kh.backend_finalproject.entitiy.PostTb;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostTb, Long> {
    // ✅메인 페이지 : 전체 지역 게시글 작성일 최근순 정렬
    @Query("SELECT new com.kh.backend_finalproject.dto.PostUserDto(u.id, p.id, u.pfImg, u.nickname, p.title, p.district, " +
            "p.imgUrl, p.writeDate) " +
            "FROM UserTb u INNER JOIN u.posts p " +
            "ORDER BY p.writeDate DESC")
    List<PostUserDto> findAllPostsWithUserDetails();

    // ✅메인 페이지 : 특정 지역 게시글 작성일 최근순 정렬
    @Query("SELECT new com.kh.backend_finalproject.dto.PostUserDto(u.id, p.id, u.pfImg, u.nickname, p.title, p.district, " +
            "p.imgUrl, p.writeDate) " +
            "FROM UserTb u INNER JOIN u.posts p " +
            "WHERE p.region = :region " +
            "ORDER BY p.writeDate DESC")
    List<PostUserDto> findRegionPostsWithUserDetails(@Param("region") RegionStatus status);

    // ✅메인 페이지 : 키워드 검색
    @Query("SELECT p FROM PostTb p WHERE p.title LIKE %:keyword% OR p.district LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<PostTb> findByKeyword(@Param("keyword") String keyword);

    // ✅메인 페이지 : 북마크 상위 5개 게시글 내림차순 정렬
    @Query("SELECT new com.kh.backend_finalproject.dto.PostBookmarkDto(p.title, COUNT(b), p.id) " +
            "FROM PostTb p JOIN p.bookmarks b " +
            "GROUP BY p " +
            "ORDER BY COUNT(b) DESC")
    Page<PostBookmarkDto> findTop5ByBookmarkCount(Pageable pageable);

    // 게시글 조회(by Id)
    Optional<PostTb> findById(Long id);

    //💗 관리자 페이지 : 전체 게시글 조회 ( 최근순 정렬)
    @Query("SELECT new com.kh.backend_finalproject.dto.PostUserDto( p.id, u.nickname, p.title, p.writeDate) " +
            "FROM UserTb u INNER JOIN u.posts p " +
            "ORDER BY p.writeDate DESC")
    List<PostUserDto> findAllPostsWithUserNickname();

    // ✅특정 게시글의 북마크 수 가져오기
    @Query("SELECT COUNT(b) FROM BookmarkTb b WHERE b.post.id = :postId")
    int findBookmarkCountByPostId(@Param("postId") Long postId);

    //💗 관리자 페이지 : 게시물 검색 (제목, 작성자)
    @Query("SELECT p FROM PostTb p JOIN p.user u WHERE p.title LIKE %:keyword% OR u.nickname LIKE %:keyword%")
    List<PostTb> findByKeywordAdminPost(@Param("keyword") String keyword);


}
