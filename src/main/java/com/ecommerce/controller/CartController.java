package com.ecommerce.controller;

import com.ecommerce.dto.AddToCartRequest;
import com.ecommerce.model.Cart;
import com.ecommerce.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/users/{userId}/cart")
@Tag(name = "Shopping Cart", description = "APIs for shopping cart management")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    @Operation(summary = "View cart", description = "Retrieve the user's shopping cart with all items")
    public ResponseEntity<Cart> viewCart(@PathVariable Long userId){
        Cart cart = cartService.cartInfo(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    @Operation(summary = "Add product to cart", description = "Add a product with specified quantity to the user's cart")
    public ResponseEntity<Cart> addProduct(@PathVariable Long userId, @Valid @RequestBody AddToCartRequest request){
        Cart cart = cartService.addProductToCart(userId, request.getProductId(), request.getQuantity());
        return new ResponseEntity<>(cart, HttpStatus.CREATED);
    }

    @PutMapping("/items")
    @Operation(summary = "Update product quantity", description = "Modify the quantity of an existing product in the cart")
    public ResponseEntity<Cart> updateProduct(@PathVariable Long userId, @Valid @RequestBody AddToCartRequest request){
        Cart cart = cartService.updateProductQuantity(userId, request.getProductId(), request.getQuantity());
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{productId}")
    @Operation(summary = "Remove product from cart", description = "Delete a specific product from the user's cart")
    public ResponseEntity<Cart> removeProduct(@PathVariable Long userId, @PathVariable Long productId){
        Cart cart = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/clear")
    @Operation(summary = "Clear cart", description = "Remove all items from the user's shopping cart")
    public ResponseEntity<String> clearCart(@PathVariable Long userId){
        cartService.clearCart(userId);
        return ResponseEntity.ok("Cart cleared successfully");
    }
}
