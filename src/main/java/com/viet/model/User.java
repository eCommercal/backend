package com.viet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.viet.domain.USER_ROLE;
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
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String fullName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;

    String email;

    String mobile;

    USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @OneToMany
    Set<Address> addresses = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    Set<Coupon> usedCoupons = new HashSet<>();
}
