package com.project.offeringservice.controller;

import com.project.offeringservice.modal.ServiceOffering;
import com.project.offeringservice.services.ServiceOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/service-offering")
@RequiredArgsConstructor
public class ServiceOfferingController {

    private final ServiceOfferingService serviceOfferingService;

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<Set<ServiceOffering>> getServicesBySalonId(
            @PathVariable Long salonId,
            @RequestParam(required = false) Long categoryId
    ) {
        return ResponseEntity.ok(serviceOfferingService.getAllServicesBySalonId(salonId, categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceOffering> getServicesById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(serviceOfferingService.getServiceById(id));
    }

    @GetMapping("/list/{ids}")
    public ResponseEntity<Set<ServiceOffering>> getServicesByIds(
            @PathVariable Set<Long> ids
    ) {
        return ResponseEntity.ok(serviceOfferingService.getServiceByIds(ids));
    }



}
