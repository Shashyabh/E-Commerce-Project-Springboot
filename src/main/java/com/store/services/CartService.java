package com.store.services;

import com.store.dtos.AddItemToCartRequest;
import com.store.dtos.CartDto;
import com.store.entities.Cart;

public interface CartService {

    //Add itme to cart
    //If cart for user is not available, then create Cart first
    //If available than directly add into cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //Remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //Clear cart
    void clearCart(String userId);

    //
    CartDto getCartUser(String userId);

}
