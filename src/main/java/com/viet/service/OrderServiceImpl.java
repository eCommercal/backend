package com.viet.service;

import com.viet.domain.OrderStatus;
import com.viet.domain.PaymentStatus;
import com.viet.model.*;
import com.viet.repository.AddressRepository;
import com.viet.repository.OrderItemRepository;
import com.viet.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {


    OrderRepository orderRepository;
    AddressRepository addressRepository;
    OrderItemRepository orderItemRepository;

    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {

        if(!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);

        // mua tu nhieu brand

        // brand 1 -> 3 ao
        // brand 2 -> 4 quan

        Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream().collect(Collectors.groupingBy(
                item -> item.getProduct().getSeller().getId()
        ));

        // tao 1 order cho 3 ao, tao 1 order khac cho 4 quan
        Set<Order> orders = new HashSet<>();

        for(Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> cartItems = entry.getValue();

            int totalOrderPrice = cartItems.stream().mapToInt(
                    CartItem::getSellingPrice
            ).sum();

            int totalItem = cartItems.stream().mapToInt(
                    CartItem::getQuantity
            ).sum();

            Order createdOrder = new Order();
            createdOrder.setSellerId(sellerId);
            createdOrder.setUser(user);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            Order savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItem> items =  new ArrayList<>();

            for(CartItem cartItem : cartItems) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setMrpPrice(cartItem.getMrpPrice());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setSize(cartItem.getSize());
                orderItem.setUserId(cartItem.getUserId());
                orderItem.setSellingPrice(cartItem.getSellingPrice());

                savedOrder.getOrderItems().add(orderItem);

                OrderItem savedOrderItem = orderItemRepository.save(orderItem);
                items.add(savedOrderItem);
            }
        }



        return orders;
    }

    @Override
    public Order findOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(() -> new Exception("Order not found"));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Order order = this.findOrderById(orderId);
        order.setOrderStatus(orderStatus);

        return orderRepository.save(order);
    }

    @Override
    public Order cancelOrder(Long orderId, User user) throws Exception {
        Order order = this.findOrderById(orderId);

        if(!user.getId().equals(order.getUser().getId())) {
            throw new Exception("you do not have permission to cancel this order");
        }

        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public OrderItem findById(Long id) throws Exception {
        return orderItemRepository.findById(id).orElseThrow(() -> new Exception("" +
                "order item not found !"));
    }
}
