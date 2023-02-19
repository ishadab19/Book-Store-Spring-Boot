package com.bridgelabz.bookstoreapp.repository;

import com.bridgelabz.bookstoreapp.model.OrderData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderData,Integer> {
    @Query("from orderData WHERE userId=:userId  ")
    List<OrderData> findByUserId(int userId);

}
