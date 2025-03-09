package com.viet.service;

import com.viet.model.Cart;
import com.viet.model.CartItem;
import com.viet.model.Product;
import com.viet.model.User;

public interface CartService {
    CartItem addCardItem(User user, Product product, String size, int quantity);

    Cart findUserCart (User user);
}
