package com.PFE.RH.Services;

import com.PFE.RH.DTO.CategoryDTO;
import com.PFE.RH.Entities.Category;
import com.PFE.RH.Mappers.CategoryMapper;
import com.PFE.RH.Repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toList());
    }

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
        Category category = categoryMapper.categoryDTOToCategory(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(savedCategory);
    }

    public CategoryDTO getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    public CategoryDTO updateCategory(Long categoryId, CategoryDTO updatedCategoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        category.setLibele(updatedCategoryDTO.getLibele());

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(updatedCategory);
    }
    public void deleteCategory(Long CategoryId) {
        categoryRepository.deleteById(CategoryId);
    }

    public CategoryDTO updatePartialCategory(Long categoryId, CategoryDTO updatedCategoryDTO) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        if (updatedCategoryDTO.getLibele() != null) {
            category.setLibele(updatedCategoryDTO.getLibele());
        }

        Category updatedCategory = categoryRepository.save(category);
        return categoryMapper.categoryToCategoryDTO(updatedCategory);
    }}