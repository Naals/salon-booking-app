package com.project.categoryservice.service.impl;

import com.project.categoryservice.exceptions.CategoryNotFoundException;
import com.project.categoryservice.exceptions.UnauthorizedAccessException;
import com.project.categoryservice.payload.dto.SalonDto;
import com.project.categoryservice.modal.Category;
import com.project.categoryservice.repository.CategoryRepository;
import com.project.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDto salonDto) {

        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }

        if (salonDto == null || salonDto.getId() == null) {
            throw new IllegalArgumentException("Salon data is invalid");
        }

        if (category.getName() == null || category.getName().isBlank()) {
            throw new IllegalArgumentException("Category name cannot be empty");
        }

        Category newCategory = new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSalonId(salonDto.getId());

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        if (categories.isEmpty()) {
            return List.of();
        }

        return categories;
    }

    @Override
    public Set<Category> getAllCategoriesBySalonId(Long salonId) {

        if (salonId == null || salonId <= 0) {
            throw new IllegalArgumentException("Invalid salon id: " + salonId);
        }

        Set<Category> categories = categoryRepository.findBySalonId(salonId);

        if (categories.isEmpty()) {
            return Set.of();
        }

        return categories;
    }

    @Override
    public Category getCategoryById(Long id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category id: " + id);
        }

        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(
                        "Category not found with id: " + id
                ));
    }

    @Override
    public void deleteCategoryById(Long id, Long salonId) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category id: " + id);
        }

        if (salonId == null || salonId <= 0) {
            throw new IllegalArgumentException("Invalid salon id: " + salonId);
        }

        Category category = getCategoryById(id);

        if (!category.getSalonId().equals(salonId)) {
            throw new UnauthorizedAccessException(
                    "You do not have permission to delete this category"
            );
        }

        categoryRepository.deleteById(id);
    }

    @Override
    public Category getCategoryByIdAndSalonId(Long id, Long salonId) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid category id: " + id);
        }

        if (salonId == null || salonId <= 0) {
            throw new IllegalArgumentException("Invalid salon id: " + salonId);
        }

        Category category = categoryRepository.findByIdAndSalonId(id, salonId);

        if (category == null) {
            throw new CategoryNotFoundException(
                    "Category not found with id: " + id + " for salon: " + salonId
            );
        }

        return category;
    }
}
