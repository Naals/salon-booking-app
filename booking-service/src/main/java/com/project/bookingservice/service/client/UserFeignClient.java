package com.project.bookingservice.service.client;

import com.project.bookingservice.payload.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("USER-SERVICE")
public interface UserFeignClient {

    @GetMapping("api/users/{userId}")
    ResponseEntity<UserDto> getUserById(
            @PathVariable("userId") Long userId) throws Exception;

    @GetMapping("/api/users/profile")
    ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws Exception;
}
