package com.ecommerce.service;

import com.ecommerce.model.Category;
import com.ecommerce.model.Product;
import com.ecommerce.repositories.CategoryRepository;
import com.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImpl implements  ProductService{

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(Long categoryId, Product product) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

        product.setCategory(category);
        Product newProduct=productRepository.save(product);
        return newProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));

       productRepository.delete(product);
        System.out.println("Deleted Product with Id "+productId);;
    }

    @Override
    public Product updateProduct(Product product ,Long productId) {
        Product savedProduct=productRepository.findById(productId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource Not Found"));
        savedProduct.setProductName(product.getProductName());
        savedProduct.setProductPrice(product.getProductPrice());
        savedProduct.setProductStock(product.getProductStock());
        return productRepository.save(savedProduct);
    }
}
