package com.viet.service;

import com.viet.model.Cart;
import com.viet.model.CartItem;
import com.viet.model.Product;
import com.viet.model.User;
import com.viet.repository.CartItemRepository;
import com.viet.repository.CartRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartServiceImpl implements CartService {

    CartItemRepository cartItemRepository;
    CartRepository cartRepository;

    @Override
    public CartItem addCardItem(User user, Product product, String size, int quantity) {
        Cart cart = this.findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);
        if(isPresent == null){
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice = quantity*product.getSellingPrice();

            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity*product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);

        }
        return isPresent;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem=0;

        for (CartItem item : cart.getCartItems()) {
            totalItem += item.getMrpPrice();
            totalDiscountedPrice += item.getSellingPrice();
            totalItem += item.getQuantity();

            cart.setCartItems(cart.getCartItems());
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalItem(totalItem);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));

        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if(mrpPrice <= 0){
//            throw new IllegalArgumentException("MrpPrice must be greater than 0");
            return 0;
        }

        double discount = mrpPrice - sellingPrice;
        double percentage = (discount / mrpPrice) * 100;

        return (int) percentage;
    }
}
