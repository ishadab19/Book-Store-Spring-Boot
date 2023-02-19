package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.model.CartData;

import java.util.List;

public interface CartServiceImpl {

    List<CartData> getCartByUserId(int userId);

    List<CartData> getCart();

    CartData addToCart(int userId, CartDTO cartDTO);


    CartData updateBookQuantityInCart(int cartId, CartDTO cartDTO);

    CartData update(int cartId, int quantity);

    void deleteById(int cartId);

    String emptyCart();

    CartData getCartByCartId(int cartId);
}