package com.viet.service;

import com.viet.domain.OrderStatus;
import com.viet.model.Address;
import com.viet.model.Cart;
import com.viet.model.Order;
import com.viet.model.User;
import com.viet.repository.AddressRepository;
import com.viet.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {


    OrderRepository orderRepository;
    AddressRepository addressRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {

        if(!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);

        // mua tu nhieu brand

        // brand 1 -> 3 ao
        // brand 2 -> 4 quan



        return Set.of();
    }

    @Override
    public Order findOrderById(Long id) {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return List.of();
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return List.of();
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        return null;
    }

    @Override
    public Order cancelOrder(Long orderId, User user) {
        return null;
    }
}
