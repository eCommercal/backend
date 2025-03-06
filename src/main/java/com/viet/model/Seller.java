package com.viet.model;

import com.viet.domain.AccountStatus;
import com.viet.domain.USER_ROLE;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String sellerName;

    String mobile;

    @Column( unique = true, nullable = false)
    String email;

    String password;

    @Embedded
    BusinessDetails businessDetails = new BusinessDetails();

    @Embedded
    BankDetails bankDetails = new BankDetails();

    @OneToOne(cascade = CascadeType.ALL)
    Address pickupAddress = new Address();

    String GSTIN;

    USER_ROLE role = USER_ROLE.ROLE_SELLER;

    boolean isEmailVerified = false;

    AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;
}
