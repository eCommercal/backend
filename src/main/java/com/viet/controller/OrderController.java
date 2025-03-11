package com.viet.controller;

import com.viet.domain.PaymentMethod;
import com.viet.model.*;
import com.viet.repository.PaymentOrderRepository;
import com.viet.response.PaymentResponse;
import com.viet.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;
    UserService userService;
    CartService cartService;
    SellerService sellerService;
    SellerReportService sellerReportService;
    PaymentService paymentService;
    PaymentOrderRepository paymentOrderRepository;



    @PostMapping
    public ResponseEntity<PaymentResponse> createOrder(@RequestBody Address shippingAddress,
                                                       @RequestParam PaymentMethod paymentMethod,
                                                       @RequestHeader("Authorization") String jwt
                                                       ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);

        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);

        PaymentOrder paymentOrder = paymentService.createOrder(user, orders);

        PaymentResponse paymentResponse = new PaymentResponse();

        String paymentUrl = paymentService.createPaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());
        paymentResponse.setPaymentUrl(paymentUrl);


        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);

    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> usersOrderHistory(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/item/{orderItemId}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable Long orderItemId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        OrderItem orderItem = orderService.findById(orderItemId);

        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Order order = orderService.cancelOrder(orderId, user);

        Seller seller = sellerService.getSellerById(order.getSellerId());
        SellerReport sellerReport = sellerReportService.getSellerReport(seller);

        sellerReport.setCancelOrders(sellerReport.getCancelOrders() + 1);
        sellerReport.setTotalRefunds(sellerReport.getTotalRefunds() + order.getTotalSellingPrice());

        return new ResponseEntity<>(order, HttpStatus.OK);
    }



}
