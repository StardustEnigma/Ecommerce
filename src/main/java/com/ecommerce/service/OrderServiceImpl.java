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

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        User user=userRepository.findById(userId).
                orElseThrow(()-> new UserNotFoundException("User Not found"));
        Cart cart=cartRepository.findByUser(user).
                orElseThrow(()-> new CartNotFoundException("Cart Not Found for user"));

        if (cart.getCartItems().isEmpty()){
            throw new EmptyCartException("Cart is Empty");
        }
        Order order=new Order();
        order.setUser(cart.getUser());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems=new ArrayList<>();
        double totalAmount=0;
        for (CartItem item: cart.getCartItems()){
            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPriceAtPurchase(item.getProduct().getProductPrice());
            orderItems.add(orderItem);
            totalAmount+=orderItem.getPriceAtPurchase()*orderItem.getQuantity();
        }
        order.setOrderItems(orderItems);
        order.setTotalAmount(totalAmount);
        Order saveOrder=orderRepository.save(order);

        cart.getCartItems().clear();
        cartRepository.save(cart);
        return saveOrder;
    }

    @Override
    public List<Order> orderHistory(Long userId) {
        return orderRepository.findByUserUserId(userId);
    }

    @Override
    public Order orderDetails(Long orderId) {
        Order order=orderRepository.findById(orderId).
                orElseThrow(()->new OrderNotFoundException("Order Not Found"));

        return order;
    }

    @Override
    public void cancelOrder(Long userId,Long orderId) {
        User user=userRepository.findById(userId).
                orElseThrow(()->new UserNotFoundException("Uer Not Found"));
        Order order=orderRepository.findById(orderId).
                orElseThrow(()->new OrderNotFoundException("Order Not Found"));

        if (!order.getUser().getUserId().equals(user.getUserId())){
            throw new OrderOwnershipException("Order does not Belong to this user");
        }
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }
}
