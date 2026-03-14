package com.project.categoryservice.controller;

import com.project.categoryservice.payload.dto.SalonDto;
import com.project.categoryservice.modal.Category;
import com.project.categoryservice.payload.response.ApiResponse;
import com.project.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        Category saveCategory = categoryService.saveCategory(category, salonDto);
        return ResponseEntity.ok(saveCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id) throws Exception {
        SalonDto salonDto = new SalonDto();
        salonDto.setId(1L);

        categoryService.deleteCategoryById(id, salonDto.getId());


        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Category deleted successfully");

        return ResponseEntity.ok(apiResponse);
    }

}
