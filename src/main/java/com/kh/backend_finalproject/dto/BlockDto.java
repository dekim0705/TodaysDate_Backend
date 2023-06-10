package com.kh.backend_finalproject.dto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BlockDto {
    private UserDto blocker;
    private UserDto blocked;
}
