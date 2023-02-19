package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.model.OrderData;

import javax.mail.MessagingException;
import java.util.List;

public interface IOrderService {

    String cancelOrder(int orderId, int userId);
    List<OrderData> userOrders(int userId);

    OrderData  placeOrder(int userId, OrderDTO orderDTO) throws MessagingException;

    List<OrderData> getAllOrders();

    int getAllOrdersNumber();
}
