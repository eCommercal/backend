package com.viet.model;

import com.viet.domain.OrderStatus;
import com.viet.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String orderId;

    @ManyToOne
    User user;

    Long sellerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> orderItems = new ArrayList<>();

    @ManyToOne
    Address shippingAddress;

    @Embedded
    PaymentDetails paymentDetails = new PaymentDetails();

    double totalMrpPrice;

    Integer totalSellingPrice;

    Integer discount;
    OrderStatus orderStatus;

    int totalItem;

    PaymentStatus paymentStatus = PaymentStatus.PENDING;

    LocalDateTime orderDate = LocalDateTime.now();
    LocalDateTime deliveryDate = orderDate.plusDays(7);
}
