package com.viet.service;

import com.viet.domain.OrderStatus;
import com.viet.model.Address;
import com.viet.model.Cart;
import com.viet.model.Order;
import com.viet.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(Long id);
    List<Order> usersOrderHistory(Long userId);
    List<Order> sellersOrder(Long sellerId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus);
    Order cancelOrder(Long orderId, User user);
}
