package com.viet.repository;

import com.viet.domain.AccountStatus;
import com.viet.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Seller findByEmail(String email);

    List<Seller> findByAccountStatus(AccountStatus status);
}
