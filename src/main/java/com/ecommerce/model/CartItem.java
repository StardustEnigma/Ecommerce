package com.ecommerce.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cartId",nullable = false)
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId",nullable = false)
    private Product product;

    @Column(nullable = false)
    private int quantity;
}
