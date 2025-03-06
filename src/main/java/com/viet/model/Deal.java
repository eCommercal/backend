package com.viet.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    Integer discount;

    @OneToOne
    HomeCategory category;
}
