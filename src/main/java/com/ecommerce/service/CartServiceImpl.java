package com.ecommerce.service;

import com.ecommerce.exception.*;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.repositories.CartItemRepository;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ProductRepository;
import com.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service implementation for shopping cart operations.
 * Manages adding, updating, and removing products from user carts.
 */
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Cart getCartByUser(Long userId) {
        return cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }

    @Override
    public double getTotal(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return cart.getCartItems().stream()
                .mapToDouble(item -> item.getProduct().getProductPrice() * item.getQuantity())
                .sum();
    }

    @Override
    public void clearCart(Long userId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

    /**
     * Retrieves or creates a cart for the user.
     *
     * @param userId the ID of the user
     * @return the user's cart
     */
    @Override
    public Cart cartInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    /**
     * Adds a product to the user's cart or increases quantity if already present.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to add
     * @param quantity the quantity to add
     * @return the updated cart
     */
    @Override
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        return cartRepository.save(cart);
    }

    /**
     * Updates the quantity of a product in the cart.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product
     * @param quantity the new quantity
     * @return the updated cart
     */
    @Override
    public Cart updateProductQuantity(Long userId, Long productId, int quantity) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new CartItemNotFoundException("Product not found in cart"));

        cartItem.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    /**
     * Removes a product from the user's cart.
     *
     * @param userId the ID of the user
     * @param productId the ID of the product to remove
     * @return the updated cart
     */
    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserUserId(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + productId));

        CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElseThrow(() -> new CartItemNotFoundException("Product not in cart"));

        cart.getCartItems().remove(cartItem);
        return cartRepository.save(cart);
    }
}
