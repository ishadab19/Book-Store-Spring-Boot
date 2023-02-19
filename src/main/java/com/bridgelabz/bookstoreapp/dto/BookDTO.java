package com.bridgelabz.bookstoreapp.dto;
import lombok.Data;
import lombok.ToString;


@Data
public @ToString class BookDTO {

    private String bookName;

    private String authorName;

    private String bookDescription;

    private String bookImg;

    private Integer price;

    private Integer quantity;
}
