package com.ecommerce.controller;

import com.ecommerce.service.CategoryService;
import com.ecommerce.model.Category;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Category Management", description = "APIs for category management operations")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/public/categories")
    @Operation(summary = "Get all categories", description = "Retrieve a list of all product categories")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    @Operation(summary = "Create a new category", description = "Add a new product category to the system")
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/categories/{categoryId}")
    @Operation(summary = "Update category details", description = "Modify an existing category's information (Admin only)")
    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){
        categoryService.updateCategory(category, categoryId);
        return new ResponseEntity<>("Category updated successfully with ID " + categoryId, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    @Operation(summary = "Delete a category", description = "Remove a category from the system (Admin only)")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        String status = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(status);
    }
}
