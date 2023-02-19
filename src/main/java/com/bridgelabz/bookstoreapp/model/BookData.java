package com.bridgelabz.bookstoreapp.model;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Component
@Entity
@Data
@NoArgsConstructor
@Table(name = "book_details")
public class BookData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    private String bookName;

    private String authorName;

    private String bookDescription;

    private String bookImg;
    private int price;

    private int quantity;

    public BookData(BookDTO bookDTO) {

        this.bookName = bookDTO.getBookName();
        this.authorName = bookDTO.getAuthorName();
        this.bookDescription = bookDTO.getBookDescription();
        this.bookImg = bookDTO.getBookImg();
        this.price = bookDTO.getPrice();
        this.quantity = bookDTO.getQuantity();

    }

}