package com.ecommerce.controller;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Tag(name = "Product Management", description = "APIs for product management operations")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/api/public/products")
    @Operation(summary = "Get all products", description = "Retrieve a list of all available products")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping("/api/admin/{categoryId}/products")
    @Operation(summary = "Create a new product", description = "Add a new product to a specific category (Admin only)")
    public ResponseEntity<String> createProduct(@Valid @RequestBody Product product, @PathVariable Long categoryId){
        productService.createProduct(categoryId, product);
        return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
    }

    @PutMapping("/api/admin/products/{productId}")
    @Operation(summary = "Update product details", description = "Modify an existing product's information (Admin only)")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @Valid @RequestBody Product product){
        productService.updateProduct(product, productId);
        return new ResponseEntity<>("Product updated successfully with Id " + productId, HttpStatus.OK);
    }

    @DeleteMapping("/api/admin/products/{productId}")
    @Operation(summary = "Delete a product", description = "Remove a product from the system (Admin only)")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
