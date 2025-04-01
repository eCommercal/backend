package com.viet.service;

import com.viet.model.CartItem;
import com.viet.model.User;
import com.viet.repository.CartItemRepository;
import com.viet.service.Impl.CartItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CartItemServiceImpl implements CartItemService {

    CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws Exception {

        CartItem item = this.findCartItemById(id);

        User user = item.getCart().getUser();

        if(user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity()*item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
       throw new Exception("you can not update another user's cart.");
    }

    @Override
    public void removeCartItem(Long userId, Long id) throws Exception {

        CartItem item = this.findCartItemById(id);

        User user = item.getCart().getUser();

        if(user.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else throw new Exception("you can not delete this item.");

    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(() ->  new Exception("cart item not found"));
    }
}
