package com.project.bookingservice.service.client;

import com.project.bookingservice.payload.dto.ServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient("OFFERING-SERVICE")
public interface OfferingServiceFeignClient {

    @GetMapping("/api/service-offering/list/{ids}")
    ResponseEntity<Set<ServiceDto>> getServicesByIds(
            @PathVariable Set<Long> ids
    );

}
