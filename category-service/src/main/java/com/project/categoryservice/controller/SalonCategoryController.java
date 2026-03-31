package com.project.categoryservice.controller;

import com.project.categoryservice.payload.dto.SalonDto;
import com.project.categoryservice.modal.Category;
import com.project.categoryservice.payload.response.ApiResponse;
import com.project.categoryservice.service.CategoryService;
import com.project.categoryservice.service.client.SalonFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;
    private final SalonFeignClient salonFeignClient;

    @PostMapping()
    public ResponseEntity<Category> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        Category saveCategory = categoryService.saveCategory(category, salonDto);
        return ResponseEntity.ok(saveCategory);
    }

    @GetMapping("salon/{salonId}/category/{id}")
    public ResponseEntity<Category> getCategoriesByIdAndSalon(
            @PathVariable Long id,
            @PathVariable Long salonId
    ) {
        Category categories = categoryService.getCategoryByIdAndSalonId(id, salonId);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SalonDto salonDto = salonFeignClient.getSalonByOwnerId(jwt).getBody();

        assert salonDto != null;
        categoryService.deleteCategoryById(id, salonDto.getId());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

}
