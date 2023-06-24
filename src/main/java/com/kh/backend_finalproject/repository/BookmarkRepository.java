package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.BookmarkTb;
import com.kh.backend_finalproject.entitiy.FolderTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<BookmarkTb, Long> {
    Optional<BookmarkTb> findByFolderAndPost(FolderTb folder, PostTb post);
    Optional<BookmarkTb> findByPostAndUser(PostTb post, UserTb user);
}
