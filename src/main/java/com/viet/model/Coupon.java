package com.viet.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String code;

    double discountPercentage;

    LocalDate validityEndDate;

    double minimumOrderValue;

    boolean isActive = true;

    @ManyToMany(mappedBy = "usedCoupons")
    Set<User> usedByUsers = new HashSet<>();

}
