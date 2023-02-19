package com.bridgelabz.bookstoreapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@Entity(name = "orderData")
public class OrderData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @CreationTimestamp
    private LocalDate orderDate;
    private int quantity;
    private String address;
    private double totalPrice;

    private int userId;
    @ManyToMany
    @JoinColumn(name = "bookId")
    private List<BookData> book;

    @OneToMany(fetch = FetchType.LAZY,orphanRemoval=true)
    @org.hibernate.annotations.ForeignKey(name = "none")
    private List<CartData> cartModel;

    private boolean cancel;



    public OrderData() {

    }

    public OrderData(int userId, String address, List<CartData> cartModel, List<BookData> orderedBooks, int totalOrderQty, double totalOrderPrice) {
        this.userId=userId;
        this.address = address;
        this.quantity = totalOrderQty;
        this.book = orderedBooks;
        this.cartModel = cartModel;
        this.totalPrice = totalOrderPrice;
    }
}

