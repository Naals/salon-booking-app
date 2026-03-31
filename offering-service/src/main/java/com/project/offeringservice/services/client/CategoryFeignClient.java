package com.project.offeringservice.services.client;

import com.project.offeringservice.payload.dto.CategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("CATEGORY-SERVICE")
public interface CategoryFeignClient {

    @GetMapping("/api/categories/salon-owner/salon/{salonId}/category/{id}")
    ResponseEntity<CategoryDto> getCategoriesByIdAndSalon(
            @PathVariable Long id,
            @PathVariable Long salonId
    );
}
