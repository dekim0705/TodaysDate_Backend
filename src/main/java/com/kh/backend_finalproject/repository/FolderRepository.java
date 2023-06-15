package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.FolderTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<FolderTb, Long> {
    Optional<FolderTb> findByNameAndUser(String name, UserTb user);

    Optional<FolderTb> findById(Long id);
}
