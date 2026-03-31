package com.project.offeringservice.controller;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.CategoryDto;
import com.project.offeringservice.payload.dto.SalonDto;
import com.project.offeringservice.payload.dto.ServiceDto;
import com.project.offeringservice.services.ServiceOfferingService;
import com.project.offeringservice.services.client.CategoryFeignClient;
import com.project.offeringservice.services.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;
    private final SalonFeignClient salonFeignClient;
    private final CategoryFeignClient categoryFeignClient;

    @PostMapping
    public ResponseEntity<ServiceOffering> createServiceOffering(
            @RequestBody ServiceDto serviceDto,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        CategoryDto categoryDto = categoryFeignClient.
                getCategoriesByIdAndSalon(serviceDto.getCategoryId(), salonDto.getId()).getBody();

        ServiceOffering serviceOfferings = serviceOfferingService.createServiceOffering(salonDto, serviceDto, categoryDto);

        return ResponseEntity.ok(serviceOfferings);
    }

    @PostMapping("{id}")
    public ResponseEntity<ServiceOffering> createServiceOffering(
            @PathVariable Long id,
            @RequestBody ServiceOffering serviceOffering
    ) {
        ServiceOffering serviceOfferings = serviceOfferingService.updateService(id, serviceOffering);

        return ResponseEntity.ok(serviceOfferings);
    }
}
