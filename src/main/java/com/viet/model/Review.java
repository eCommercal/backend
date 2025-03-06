package com.viet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false)
    String reviewText;

    @Column(nullable = false)
    double rating;

    @ElementCollection
    List<String> productImages;

    @ManyToOne
    @JsonIgnore
    Product product;

    @ManyToOne
    User user;

    @Column(nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();
}
