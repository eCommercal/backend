package com.viet.service;

import com.viet.model.Product;
import com.viet.model.User;
import com.viet.model.Wishlist;
import com.viet.repository.WishlistRepository;
import com.viet.service.Impl.WishListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyRole('CUSTOMER')")
    public Wishlist addProductToWishlist(User user, Product product) {
        Wishlist wishlist = this.getWishlistByUserId(user);

        System.out.println("User ID: " + user.getId());
        System.out.println("Wishlist ID: " + wishlist.getId());

        if(wishlist.getProducts().contains(product)) {
            wishlist.getProducts().remove(product);
        }else {
            wishlist.getProducts().add(product);
        }

        return wishlistRepository.save(wishlist);
    }
}
