package com.ecommerce.controller;

import com.ecommerce.service.CategoryService;
import com.ecommerce.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/api/public/categories")
    public ResponseEntity<List<Category>> getCategories(){
        List<Category> categories = categoryService.getCategories();
        return new ResponseEntity<>( categories,HttpStatus.OK);
    }

    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category Added Successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String status=categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        }
        catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());

        }
    }
    @PutMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
        try{
            Category savedCategory=categoryService.updateCategory(category,categoryId);
            return new ResponseEntity<>("Catgeory updated with Category Id "+categoryId,HttpStatus.OK);
        }
        catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(),e.getStatusCode());

        }
    }
}
