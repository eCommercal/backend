package com.viet.service.Impl;

import com.viet.model.Cart;
import com.viet.model.Coupon;
import com.viet.model.User;

import java.util.List;

public interface CouponService {

    Cart applyCoupon(String couponCode, User user, double orderValue) throws Exception;
    Cart removeCoupon(String couponCode, User user) throws Exception;
    Coupon findCouponById(Long id) throws Exception;
    Coupon createCoupon(Coupon coupon);
    List<Coupon> getAllCoupons();
    void deleteCoupon(Long id) throws Exception;
}
