package com.viet.service;

import com.viet.model.Product;
import com.viet.model.User;
import com.viet.model.Wishlist;
import com.viet.repository.WishlistRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WishListServiceImpl implements WishListService {

    WishlistRepository wishlistRepository;

    @Override
    public Wishlist createWishlist(User user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishlistRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(User user) {
        Wishlist wishlist = wishlistRepository.findByUserId(user.getId());

        if (wishlist == null) {
           wishlist = createWishlist(user);
        }

        return wishlist;
    }

    @Override
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = this.getWishlistByUserId(user);

        if(wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
        }else {
            wishlist.getProducts().add(product);
        }

        return wishlistRepository.save(wishlist);
    }
}
