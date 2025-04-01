package com.viet.service.Impl;

import com.viet.model.Product;
import com.viet.model.User;
import com.viet.model.Wishlist;

public interface WishListService {
    Wishlist createWishlist(User user);
    Wishlist getWishlistByUserId(User user);

    Wishlist addProductToWishlist(User user, Product product);
}
