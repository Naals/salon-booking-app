package com.project.notificationservice.payload.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;

    private String fullName;

    private String email;
    private String phone;

    private String role;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
