package com.kh.backend_finalproject.repository;
import com.kh.backend_finalproject.dto.PushDto;
import com.kh.backend_finalproject.entitiy.PushTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PushRepository extends JpaRepository<PushTb, Long> {
    List<PushTb> findByUserOrderBySendDateDesc(UserTb userTb);
}
