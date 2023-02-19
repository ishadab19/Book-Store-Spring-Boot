package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import lombok.Data;

import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Data
@Entity
@Table(name = "cart_details")
public class CartData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartId;

    @ManyToOne
    @JoinColumn(name = "bookId")
    public BookData book;

    @OneToOne
    @JoinColumn(name = "userId")
    public UserData user;

    private int quantity;

    public CartData() {

    }

    public CartData(UserData user, BookData book, double cartPrice ,CartDTO cartDTO) {
        this.book=book;
        this.user=user;
        this.quantity=cartDTO.getQuantity();
        this.totalPrice=cartPrice;
    }


    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private double totalPrice;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public BookData getBook() {
        return book;
    }

    public void setBook(BookData book) {
        this.book = book;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }



    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


}