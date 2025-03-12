package com.viet.service;

import com.viet.model.Cart;
import com.viet.model.Coupon;
import com.viet.model.User;
import com.viet.repository.CartRepository;
import com.viet.repository.CouponRepository;
import com.viet.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CouponServiceImpl implements CouponService {

    CouponRepository couponRepository;
    CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Cart applyCoupon(String couponCode, User user, double orderValue) throws Exception {

        Coupon coupon = couponRepository.findByCode(couponCode);

        Cart cart = cartRepository.findByUserId(user.getId());

        if(coupon == null) {
            throw new Exception("coupon not found");
        }

        if(user.getUsedCoupons().contains(coupon)) {
            throw new Exception("coupon already used");
        }

        if(orderValue < coupon.getMinimumOrderValue()) {
            throw new Exception("coupon order value is less than minimum order value " + coupon.getMinimumOrderValue());
        }

        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())){
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice =( cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;
            cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountedPrice);
            cart.setCouponCode(couponCode);
            cartRepository.save(cart);
            return cart;

        }


        throw new Exception("coupon not valid " + coupon);
    }

    @Override
    public Cart removeCoupon(String couponCode, User user) throws Exception {

        Coupon coupon = couponRepository.findByCode(couponCode);
        if(coupon == null) {
            throw new Exception("coupon not found");
        }

        Cart cart = cartRepository.findByUserId(user.getId());

        double discountedPrice =( cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;
        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + discountedPrice);
        cart.setCouponCode(null);
        cartRepository.save(cart);

        return cart;
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepository.findById(id).orElseThrow(() -> new Exception("coupon not found"));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {
        this.findCouponById(id);
        couponRepository.deleteById(id);
    }
}
