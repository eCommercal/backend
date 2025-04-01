package com.viet.controller;

import com.viet.domain.OrderStatus;
import com.viet.model.Order;
import com.viet.model.Seller;
import com.viet.service.Impl.OrderService;
import com.viet.service.Impl.SellerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/orders")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SellerOrderController {

    OrderService orderService;
    SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrdersOfSeller(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);

        List<Order> orders = orderService.sellersOrder(seller.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);

    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long orderId,
                                                         @PathVariable OrderStatus orderStatus) throws Exception {
        Order order = orderService.updateOrderStatus(orderId, orderStatus);

        return new ResponseEntity<>(order, HttpStatus.OK);

    }

}
