package com.ecommerce.service;

import com.ecommerce.exception.CategoryNotFoundException;
import com.ecommerce.model.Category;
import com.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for category management.
 * Handles CRUD operations for product categories.
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    /**
     * Deletes a category by ID.
     *
     * @param categoryId the ID of the category to delete
     * @return success message
     * @throws CategoryNotFoundException if category doesn't exist
     */
    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        categoryRepository.delete(category);
        return "Category with ID " + categoryId + " deleted successfully";
    }

    /**
     * Updates a category's information.
     *
     * @param category the category with updated information
     * @param categoryId the ID of the category to update
     * @return the updated category
     * @throws CategoryNotFoundException if category doesn't exist
     */
    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + categoryId));

        savedCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(savedCategory);
    }
}
