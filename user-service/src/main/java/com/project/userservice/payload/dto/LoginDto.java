package com.project.userservice.payload.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String password;
}
