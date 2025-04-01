package com.viet.controller;

import com.viet.model.Cart;
import com.viet.model.Coupon;
import com.viet.model.User;
import com.viet.service.Impl.CartService;
import com.viet.service.Impl.CouponService;
import com.viet.service.Impl.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminCouponController {

    CouponService couponService;
    UserService userService;
    CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(@RequestParam String apply,
                                            @RequestParam String code,
                                            @RequestParam double orderValue,
                                            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart;

        if(apply.equals("true")){
            cart = couponService.applyCoupon(code, user, orderValue);
        } else {
            cart = couponService.removeCoupon(code, user);
        }

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon (@RequestBody Coupon coupon){
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return new ResponseEntity<>(createdCoupon, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception {

        couponService.deleteCoupon(id);
        return new ResponseEntity<>("Coupon delete success",HttpStatus.OK);

    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<Coupon>> getAllCoupon(){
        List<Coupon> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

}
