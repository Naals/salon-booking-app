package com.project.offeringservice.controller;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.payload.dto.CategoryDto;
import com.project.offeringservice.payload.dto.SalonDto;
import com.project.offeringservice.payload.dto.ServiceDto;
import com.project.offeringservice.services.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/service-offering/salon-owner")
@RequiredArgsConstructor
public class SalonServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @PostMapping
    public ResponseEntity<ServiceOffering> createServiceOffering(
            @RequestBody ServiceDto serviceDto
    ) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(serviceDto.getCategoryId());

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
