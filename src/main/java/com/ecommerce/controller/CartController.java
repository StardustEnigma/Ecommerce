package com.ecommerce.controller;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/users/{userId}/cart")

public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping

    public ResponseEntity<Cart> viewCart(@PathVariable Long userId){
        Cart cart = cartService.cartInfo(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")

    public ResponseEntity<Cart> addProduct(@PathVariable Long userId, @Valid @RequestBody AddToCartRequest request){
        Cart cart = cartService.addProductToCart(userId, request.getProductId(), request.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PutMapping("/items")

    public ResponseEntity<Cart> updateProduct(@PathVariable Long userId, @Valid @RequestBody AddToCartRequest request){
        Cart cart = cartService.updateProductQuantity(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Cart> removeProduct(@PathVariable Long userId, @PathVariable Long productId){
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/clear")
    public ResponseEntity<String> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
