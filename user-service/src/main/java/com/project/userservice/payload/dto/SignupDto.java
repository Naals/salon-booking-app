package com.project.userservice.payload.dto;

import com.project.userservice.domain.UserRole;
import lombok.Data;

@Data
public class SignupDto {

    private String fullName;
    private String email;
    private String password;
    private String username;
    private UserRole role;
}
