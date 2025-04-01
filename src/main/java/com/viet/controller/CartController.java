package com.viet.controller;

import com.viet.model.Cart;
import com.viet.model.CartItem;
import com.viet.model.Product;
import com.viet.model.User;
import com.viet.request.AddItemRequest;
import com.viet.response.ApiResponse;
import com.viet.service.Impl.CartItemService;
import com.viet.service.Impl.CartService;
import com.viet.service.Impl.ProductService;
import com.viet.service.Impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartController {

    CartService cartService;
    CartItemService cartItemService;
    UserService userService;
    ProductService productService;


    @GetMapping
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@RequestHeader("Authorization") String jwt,
                                                @RequestBody AddItemRequest req) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Product product = productService.findProductById(req.getProductId());

        CartItem cartItem = cartService.addCardItem(user, product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("add item to cart successfully");

        return new ResponseEntity<>(cartItem, HttpStatus.OK);

    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res = new ApiResponse();
        res.setMessage("item removed from cart successfully");

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable("cartItemId") Long cartItemId,
                                                   @RequestBody CartItem cartItem,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        CartItem updateCartItem = null;

        if(cartItem.getQuantity() > 0){
            updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }

        return new ResponseEntity<>(updateCartItem, HttpStatus.OK);

    }
}
