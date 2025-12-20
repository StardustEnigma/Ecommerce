package com.ecommerce.service;

import com.ecommerce.model.Cart;

public interface CartService {

    Cart getCartByUser(Long userId);

    double getTotal(Long cartId);

    void clearCart(Long cartId);

    Cart cartInfo(Long userId);

    Cart addProductToCart(Long userId, Long productId, int quantity);

    Cart updateProductQuantity(Long userId, Long productId, int quantity);

    Cart removeProductFromCart(Long userId, Long productId);




}
