package com.ekart.service;

import com.ekart.model.Cart;


public interface CartService {

    Cart getCartById (int cartId);

    void update(Cart cart);
}
