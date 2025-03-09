package com.viet.repository;

import com.viet.model.Cart;
import com.viet.model.CartItem;
import com.viet.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
