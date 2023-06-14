package com.kh.backend_finalproject.dto;
import com.kh.backend_finalproject.constant.RegionStatus;
import com.kh.backend_finalproject.entitiy.PinTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class PostPinDto {
    private Long userId;
    private PostTb post;
    private List<PinTb> pins;
}
