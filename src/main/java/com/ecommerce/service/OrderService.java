package com.ecommerce.service;

import com.ecommerce.model.Order;

import java.util.List;

public interface OrderService {

    Order placeOrder(Long userId);

    List<Order> orderHistory(Long userId);

    Order orderDetails(Long orderId);

    void cancelOrder(Long userId,Long orderId);

}
