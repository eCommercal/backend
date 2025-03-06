package com.viet.model;

import com.viet.domain.PaymentMethod;
import com.viet.domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Long amount;
    PaymentOrderStatus status = PaymentOrderStatus.PENDING;

    PaymentMethod paymentMethod;

    String paymentLinkId;

    @ManyToOne
    User user;

    @OneToMany
    Set<Order> orders = new HashSet<>();


}
