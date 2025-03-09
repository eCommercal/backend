package com.viet.service;

import com.viet.model.CartItem;

public interface CartItemService {
    CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception;
    void removeCartItem(Long userId, Long id) throws Exception;
    CartItem findCartItemById(Long id) throws Exception;
}
