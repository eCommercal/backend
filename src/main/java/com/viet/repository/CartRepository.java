package com.viet.repository;

import com.viet.model.Cart;
import com.viet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long id);
}
