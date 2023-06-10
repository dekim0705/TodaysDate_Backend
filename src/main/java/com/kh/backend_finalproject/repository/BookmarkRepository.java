package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.BookmarkTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<BookmarkTb, Long> {
}
