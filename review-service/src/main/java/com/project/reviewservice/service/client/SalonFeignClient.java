package com.project.reviewservice.service.client;

import com.project.reviewservice.payload.dto.SalonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("SALON_SERVICE")
public interface SalonFeignClient {

    @GetMapping("/api/salons/owner")
    ResponseEntity<SalonDto> getSalonByOwnerId(
            @RequestHeader("Authorization") String jwt) throws Exception;

    @GetMapping("/api/salons/{id}")
    ResponseEntity<SalonDto> getSalonById(
            @PathVariable("id") Long salonId);
}
