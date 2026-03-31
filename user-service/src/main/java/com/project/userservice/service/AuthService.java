package com.project.userservice.service;

import com.project.userservice.payload.dto.SignupDto;
import com.project.userservice.payload.response.AuthResponse;

public interface AuthService {

    AuthResponse login(String username, String password) throws Exception;

    AuthResponse signup(SignupDto req) throws Exception;

    AuthResponse getAccessTokenFromRefreshToken(String refreshToken) throws Exception;
}
