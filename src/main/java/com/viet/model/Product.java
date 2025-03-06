package com.viet.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String title;

    String description;

    int mrpPrice;

    int sellingPrice;

    int discountPercentage;

    int quantity;

    String color;

    // tao mot table rieng
    @ElementCollection
    List<String> images = new ArrayList<>();

    int numRatings;

    @ManyToOne
    Category category;

    @ManyToOne
    Seller seller;

    LocalDateTime createdAt;

    String sizes;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviews = new ArrayList<>();

}
