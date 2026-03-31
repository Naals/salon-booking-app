package com.project.categoryservice.service;

import com.project.categoryservice.payload.dto.SalonDto;
import com.project.categoryservice.modal.Category;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    Category saveCategory(Category category, SalonDto salonDto);
    List<Category> getAllCategories();
    Set<Category> getAllCategoriesBySalonId(Long salonId);
    Category getCategoryById(Long id);
    void deleteCategoryById(Long id, Long salonId) throws Exception;
    Category getCategoryByIdAndSalonId(Long id, Long salonId);
}
