package com.bridgelabz.bookstoreapp.dto;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private int quantity;
    private String address;
    private int bookId;
    private int cartId;
}

