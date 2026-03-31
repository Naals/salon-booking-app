package com.project.userservice.controller;

import com.project.userservice.payload.dto.LoginDto;
import com.project.userservice.payload.dto.SignupDto;
import com.project.userservice.payload.response.AuthResponse;
import com.project.userservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupDto req) throws Exception {
        AuthResponse res = authService.signup(req);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login") // Fix: Change path to /login
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto req) throws Exception {
        // Fix: Call the login method instead of signup
        AuthResponse res = authService.login(req.getUsername(), req.getPassword());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/access-token/refresh-token/{refreshToken}")
    public ResponseEntity<AuthResponse> getAccessToken(
            @PathVariable String refreshToken
    ) throws Exception {
        AuthResponse authResponse = authService.getAccessTokenFromRefreshToken(refreshToken);

        return ResponseEntity.ok(authResponse);
    }
}
