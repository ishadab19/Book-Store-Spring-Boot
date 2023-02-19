package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.CartData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
//import java.util.Optional;


@Repository
public interface CartRepository extends JpaRepository<CartData, Integer> {

    @Query("from CartData WHERE cartId=:cartId  ")
    CartData findByCartId( int cartId);

    @Query("from CartData WHERE user.userId=:userId  ")
    List<CartData> findByUserId(int userId);

    @Query(" FROM CartData  WHERE book.bookId=:bookId AND user.userId=:userId")
    Integer getExistingItemOfCart(int bookId, int userId);
}

