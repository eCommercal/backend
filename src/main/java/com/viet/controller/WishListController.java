package com.viet.controller;

import com.viet.model.Product;
import com.viet.model.User;
import com.viet.model.Wishlist;
import com.viet.service.Impl.ProductService;
import com.viet.service.Impl.UserService;
import com.viet.service.Impl.WishListService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WishListController {

    WishListService wishListService;
    UserService userService;
    ProductService productService;

    @GetMapping
    public ResponseEntity<Wishlist> getWishListByUserId( @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Wishlist wishlist = wishListService.getWishlistByUserId(user);

        return new ResponseEntity<>(wishlist, HttpStatus.OK);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<Wishlist> addProductToWishList(@RequestHeader("Authorization") String jwt,
                                                         @PathVariable Long productId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);

        Wishlist updateWishlist = wishListService.addProductToWishlist(user, product);
        return new ResponseEntity<>(updateWishlist, HttpStatus.OK);

    }
}
