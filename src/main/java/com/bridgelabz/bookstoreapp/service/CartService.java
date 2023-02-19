package com.bridgelabz.bookstoreapp.service;

import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookData;
import com.bridgelabz.bookstoreapp.model.CartData;
import com.bridgelabz.bookstoreapp.model.UserData;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import com.bridgelabz.bookstoreapp.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CartService implements CartServiceImpl {



    @Autowired
    private BookService bookService;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private UserService userService;

    @Override
    public List<CartData> getCartByUserId(int userId){
        List<CartData> cartdata = cartRepository.findByUserId(userId);
        if(cartdata.isEmpty())
            throw new BookStoreException("Cart details with UserId " + userId + " does not exit..!");
         return cartdata;
    }

    @Override
    public List<CartData> getCart(){
        return cartRepository.findAll();
    }


    @Override
    public CartData addToCart(int userId, CartDTO cartDTO) {

        BookData book = bookService.getBookModelById(cartDTO.getBookId());
        UserData user = userService.getUserDataById(userId);
        Integer existingDataId = cartRepository.getExistingItemOfCart(cartDTO.getBookId(), userId);
        if(existingDataId == null && book != null) {
            if (cartDTO.getQuantity() <= book.getQuantity() ) {
                double cartPrice = (book.getPrice() * cartDTO.getQuantity());
                CartData cart = new CartData(user, book, cartPrice, cartDTO);
                log.info("Item added to cart!");
                return cartRepository.save(cart);
            }
            else throw new BookStoreException("Book quantity is not enough!");
        }
        else {
            CartData updatedCart = this.updateBookQuantityInCart(existingDataId, cartDTO);
            return updatedCart;
        }
    }

    @Override
    public CartData updateBookQuantityInCart(int cartId, CartDTO cartDTO) {
        CartData cart = this.getCartByCartId(cartId);
        BookData book = bookService.getBookModelById(cartDTO.bookId);
        if (cart.getBook().getQuantity() >= book.getQuantity()) {
            cart.setQuantity(cartDTO.quantity);
            cart.setTotalPrice((book.getPrice() * cartDTO.getQuantity()));
            return cartRepository.save(cart);

            }
        else {
            throw new BookStoreException("Book quantity is not enough!");
        }
    }

    @Override
    public CartData update(int cartId, int quantity){
        CartData cartModel = cartRepository.findById(cartId).get();

        if (cartRepository.findById(cartId).isPresent() && cartModel.book.getQuantity() >= quantity){
               cartModel.setQuantity(quantity);
               cartModel.setTotalPrice(cartModel.getQuantity() * cartModel.getBook().getPrice());
               return cartRepository.save(cartModel);

        }
        else   throw new BookStoreException(" book Not found with book Id ");
    }

    @Override
    public void deleteById(int cartId){
        CartData cartModel = this.getCartByCartId(cartId);
        cartRepository.delete(cartModel);
    }

    @Override
    public String emptyCart(){
        cartRepository.deleteAll();
        return "Your cart is empty..!";
    }

    @Override
    public CartData getCartByCartId(int cartId) {
        return cartRepository.findByCartId(cartId);
    }

}
