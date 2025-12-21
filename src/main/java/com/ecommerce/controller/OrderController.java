package com.ecommerce.controller;

import com.ecommerce.model.Order;
import com.ecommerce.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public/users/{userId}/orders")
@Tag(name = "Order Management", description = "APIs for order management operations")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    @Operation(summary = "Place a new order", description = "Create an order from the user's cart items")
    public ResponseEntity<Order> placeOrder(@PathVariable Long userId) {
        Order order = orderService.placeOrder(userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    @GetMapping("/history")
    @Operation(summary = "Get order history", description = "Retrieve all orders placed by the user")
    public ResponseEntity<List<Order>> orderHistory(@PathVariable Long userId) {
        List<Order> orders = orderService.orderHistory(userId);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/cancel/{orderId}")
    @Operation(summary = "Cancel an order", description = "Cancel an existing order by its ID")
    public ResponseEntity<String> cancelOrder(@PathVariable Long userId, @PathVariable Long orderId) {
        orderService.cancelOrder(userId, orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }
}