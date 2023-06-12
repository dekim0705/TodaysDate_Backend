package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserTb, Long> {
    UserTb findByEmail(String email);
}
