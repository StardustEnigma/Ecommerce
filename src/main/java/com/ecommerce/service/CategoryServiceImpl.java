package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @Override
    public String deleteCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        categoryRepository.delete(category);
        return "Category with category id :"+categoryId+" deleted successfully";
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category SavedCategory=categoryRepository.findById(categoryId).
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        SavedCategory.setCategoryName(category.getCategoryName());
        return categoryRepository.save(SavedCategory);

    }
}
