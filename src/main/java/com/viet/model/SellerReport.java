package com.viet.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SellerReport {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @OneToOne
    Seller seller;

    Long totalEarnings = 0L;
    Long totalSales = 0L;
    Long totalRefunds = 0L;
    Long totalTax = 0L;
    Long netEarnings = 0L;

    Integer totalOrders = 0;
    Integer cancelOrders = 0;
    Integer totalTransactions = 0;

}
