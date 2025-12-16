package com.ecommerce.service;

import com.ecommerce.model.Product;

import java.util.List;

public interface ProductService {
    Product createProduct(Long categoryId,Product product);
    List<Product> getAllProducts();
    void deleteProduct(Long productId);
    Product updateProduct(Product product,Long productId);
}
