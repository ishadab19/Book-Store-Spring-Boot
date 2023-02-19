package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.OrderDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookData;
import com.bridgelabz.bookstoreapp.model.CartData;
import com.bridgelabz.bookstoreapp.model.OrderData;
import com.bridgelabz.bookstoreapp.model.UserData;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import com.bridgelabz.bookstoreapp.repository.OrderRepository;
import com.bridgelabz.bookstoreapp.repository.UserRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private UserRegistrationRepository userRepo;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private EmailSenderService mailService;

    @Autowired
    private BookService bookService;

    List<OrderData> orderList = new ArrayList<>();

    @Override
    public OrderData placeOrder(int userId, OrderDTO orderDTO) {

        List<CartData> cartModel = cartRepository.findByUserId(userId);
        UserData user = userRepo.findById(userId).orElse(null);
        user.setAddress(orderDTO.getAddress());

        if (!cartModel.isEmpty()) {
            {
                double totalOrderPrice = 0;
                int totalOrderQty = 0;
                List<BookData> orderedBooks = new ArrayList<>();

                String address = "";
                for (int i = 0; i < cartModel.size(); i++) {
                    totalOrderPrice += cartModel.get(i).getTotalPrice();
                    totalOrderQty += cartModel.get(i).getQuantity();
                    orderedBooks.add(cartModel.get(i).getBook());
                }
                if (orderDTO.getAddress() == null) {
                    address = user.getAddress();
                } else
                    address = orderDTO.getAddress();
                OrderData newOrder = new OrderData(userId, address, cartModel, orderedBooks, totalOrderQty, totalOrderPrice);
                orderList.add(newOrder);
                orderRepository.save(newOrder);

                mailService.sendEmail(user.getEmail(), "Order Placed",
                        "Order Details:" +
                                "\n" +
                                "\n" + "Order ID ####### " + newOrder.getOrderId() +
                                "\n" + "Order Address :" + newOrder.getAddress() +
                                "\n" + "Order Quantity :" + newOrder.getQuantity() +
                                "\n" + "Order Date :" + newOrder.getOrderDate() +
                                "\n" + "Order Price :" + newOrder.getTotalPrice());

                for (int i = 0; i < cartModel.size(); i++) {
                    BookData book = cartModel.get(i).getBook();
                    int updatedQty = book.getQuantity() - cartModel.get(i).getQuantity();
                    book.setQuantity(updatedQty);
                    cartRepository.deleteById(cartModel.get(i).getCartId());
                }
                return newOrder;
            }
        } else {
            throw (new BookStoreException("Sorry we cannot placed your Order...! "));
        }
    }

    @Override
    public List<OrderData> getAllOrders() {
        List<OrderData> getOrder = orderRepository.findAll();
        return getOrder;
    }

    @Override
    public int getAllOrdersNumber() {
        List<OrderData> getOrder =getAllOrders();
        int count = 0;
        for(int i = 0; getOrder.size()> i; i++){
            count++;
        }
        return count;
    }

    @Override
    public List<OrderData> userOrders(int userId) {
        return orderRepository.findByUserId(userId);
    }


    @Override
    public String cancelOrder(int orderId, int userId) {
        OrderData order = orderRepository.findById(orderId).orElse(null);
        List<BookData> book = order.getBook();
        UserData user = userRepo.findById(userId).orElse(null);
        if (user != null) {

                if (order != null) {
                    order.setCancel(true);
                    mailService.sendEmail(user.getEmail(), "For Cancel Order", "Order Id " + orderId + "\n" + order);
                    orderRepository.save(order);

                    System.out.println(book);
                    for (int j = 0; j < orderList.size(); j++) {
                        if (orderList.get(j).getOrderId() == orderId) {
                            for (int i = 0; i < book.size(); i++) {

                                    int orderedBookId = orderList.get(j).getCartModel().get(i).getBook().getBookId();
                                    int orderedQuantity = orderList.get(j).getCartModel().get(j).getQuantity();
                                    System.out.println(orderedQuantity);
                                    int bookId = book.get(i).getBookId();
                                    if (orderedBookId == bookId) {
                                        BookData book1 = bookService.getBookModelById(order.getBook().get(i).getBookId());
                                        bookService.updateBookQuantity(book1.getBookId(), book1.getQuantity() + orderedQuantity);
                                    }

                            }
                        }
                    }
                    return "Order Cancelled";
                }

            }
        else {
            throw new BookStoreException("Order is already canceled!");
        }
        return null;
    }
}