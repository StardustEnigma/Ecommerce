package com.ecommerce.repositories;

import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<Cart,Long> {
    Optional<Cart> findByUserUserId(Long userId);

    Optional<Cart> findByUser(User user);
}
