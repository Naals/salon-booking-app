package com.project.offeringservice.services.client;

import com.project.offeringservice.payload.dto.SalonDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient("SALON_SERVICE")
public interface SalonFeignClient {

    @GetMapping("/api/salons/owner")
    ResponseEntity<SalonDto> getSalonByOwnerId(
            @RequestHeader("Authorization") String jwt) throws Exception;
}
