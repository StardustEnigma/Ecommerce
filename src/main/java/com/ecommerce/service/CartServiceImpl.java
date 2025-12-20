package com.ecommerce.service;

import com.ecommerce.exception.CartItemNotFoundException;
import com.ecommerce.exception.CartNotFoundException;
import com.ecommerce.exception.ProductNotFoundException;
import com.ecommerce.exception.UserNotFoundException;
import com.ecommerce.model.Cart;
import com.ecommerce.model.CartItem;

import com.ecommerce.model.Product;
import com.ecommerce.repositories.CartItemRepository;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Cart getCartByUser(Long userId) {
        Cart cart=cartRepository.findByUserUserId(userId).
                orElseThrow(()-> new UserNotFoundException("User Not Found"));
        return cart;
    }

    @Override
    public double getTotal(Long cartId) {
        Cart cart=cartRepository.findById(cartId).
                orElseThrow(()-> new CartNotFoundException("Cart Not Found"));

        double total=0;
        for (CartItem item: cart.getCartItems()){
            total +=item.getProduct().getProductPrice()* item.getQuantity();
        }
        return total;
    }

    @Override
    public void clearCart(Long cartId) {
        Cart cart=cartRepository.findById(cartId).
                orElseThrow(()->new CartNotFoundException("Cart Not Found"));

        cart.getCartItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public Cart cartInfo(Long userId) {
        Cart cart=cartRepository.findByUserUserId(userId).
                orElseThrow(()->new UserNotFoundException("User Not Found"));
        return cart;
    }

    @Override
    public Cart addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart= cartRepository.findByUserUserId(userId).
                orElseThrow(()->new UserNotFoundException("User Not Found"));

        Product product=productRepository.findById(productId).
                orElseThrow(()->new ProductNotFoundException("Product Not Found"));

        CartItem cartItem=cartItemRepository.findByCartAndProduct(cart,product).orElse(null);

        if (cartItem==null){
            cartItem=new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateProductQuantity(Long userId, Long productId, int quantity) {
        Cart cart= cartRepository.findByUserUserId(userId).
                orElseThrow(()->new UserNotFoundException("User Not Found with id"+userId));

        Product product=productRepository.findById(productId).
                orElseThrow(()->new ProductNotFoundException("Product Not Found"));

        CartItem cartItem=cartItemRepository.findByCartAndProduct(cart,product).
                orElseThrow(()->new CartItemNotFoundException("Product Not Found in cart"));

            cartItem.setQuantity(quantity);

        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(Long userId, Long productId) {
        Cart cart= cartRepository.findByUserUserId(userId).
                orElseThrow(()->new UserNotFoundException("User Not Found with id"+userId));

        Product product=productRepository.findById(productId).
                orElseThrow(()->new ProductNotFoundException("Product Not Found with id"+productId));

        CartItem cartItem=cartItemRepository.findByCartAndProduct(cart,product).
                orElseThrow(()->new CartItemNotFoundException("Product Not in Cart"));

        cart.getCartItems().remove(cartItem);

        return cartRepository.save(cart);
    }
}
