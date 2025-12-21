package com.ecommerce.service;

import com.ecommerce.exception.*;
import com.ecommerce.model.*;
import com.ecommerce.repositories.CartRepository;
import com.ecommerce.repositories.OrderRepository;
import com.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Service implementation for order management.
 * Handles order creation, history retrieval, and cancellation.
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    /**
     * Places a new order from the user's cart.
     * Validates user and cart existence, creates order items, calculates total amount,
     * and clears the cart after order placement.
     *
     * @param userId the ID of the user placing the order
     * @return the created Order object
     * @throws UserNotFoundException if user doesn't exist
     * @throws CartNotFoundException if user's cart doesn't exist
     * @throws EmptyCartException if cart is empty
     */
    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: " + userId));

        if (cart.getCartItems().isEmpty()) {
            throw new EmptyCartException("Cannot place order: cart is empty");
        }

        // Create order
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        // Convert cart items to order items and calculate total
        List<OrderItem> orderItems = new ArrayList<>();
        double totalAmount = 0;

        for (CartItem item : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getProduct().getProductPrice());
            orderItems.add(orderItem);
            totalAmount += orderItem.getPriceAtPurchase() * orderItem.getQuantity();
        }

        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        // Clear cart after successful order placement
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    @Override
    public List<Order> orderHistory(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return orderRepository.findByUserUserId(userId);
    }

    @Override
    public Order orderDetails(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

    /**
     * Cancels an existing order.
     * Validates order ownership before allowing cancellation.
     *
     * @param userId the ID of the user requesting cancellation
     * @param orderId the ID of the order to cancel
     * @throws UserNotFoundException if user doesn't exist
     * @throws OrderNotFoundException if order doesn't exist
     * @throws OrderOwnershipException if order doesn't belong to the user
     */
    @Override
    public void cancelOrder(Long userId, Long orderId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        if (!order.getUser().getUserId().equals(user.getUserId())) {
            throw new OrderOwnershipException("Order does not belong to this user");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
