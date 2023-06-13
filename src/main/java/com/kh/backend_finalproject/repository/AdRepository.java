package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.entitiy.AdTb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<AdTb, Long> {

}
