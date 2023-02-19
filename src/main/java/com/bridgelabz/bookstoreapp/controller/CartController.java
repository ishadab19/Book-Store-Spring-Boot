package com.bridgelabz.bookstoreapp.controller;


import com.bridgelabz.bookstoreapp.dto.CartDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;

import com.bridgelabz.bookstoreapp.model.CartData;
import com.bridgelabz.bookstoreapp.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin( allowedHeaders = "*", origins = "*")
@RequestMapping("/cart")
public class CartController {

    @Autowired
    CartService cartService;

    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getCart() {
        List<CartData> cartModel = cartService.getCart();
        ResponseDTO responseDTO = new ResponseDTO("Getting all successfully",cartModel);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/getById/{userId}")
    public ResponseEntity<ResponseDTO> getcartById(@PathVariable int userId) {
        List<CartData> cartModel = cartService.getCartByUserId(userId);
        ResponseDTO responseDTO = new ResponseDTO("Record getting by ID : "+userId+" successfully",cartModel);
        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @PostMapping("/addToCart/{userId}")
    public ResponseEntity<ResponseDTO> addToCart(@PathVariable("userId") int userId, @RequestBody CartDTO cartDTO){
        CartData cart = cartService.addToCart(userId,cartDTO);
        ResponseDTO responseDto = new ResponseDTO("Cart Added  Successfully!!",cart);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/updateQuantity/{cartId}/{quantity}")
    public ResponseEntity<ResponseDTO> updateByCartId(@PathVariable int cartId,@PathVariable int quantity){
        CartData cartModel=cartService.update(cartId,quantity);
        ResponseDTO responseDTO= new ResponseDTO("Update cart by id "+cartId+" successfully..!",cartModel);
        return new ResponseEntity<>(responseDTO,HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<ResponseDTO> deleteById(@PathVariable int cartId) {
        cartService.deleteById(cartId);
        ResponseDTO respDTO = new ResponseDTO("Deleted Successfully", "Deleted id: " + cartId);
        return new ResponseEntity<ResponseDTO>(respDTO, HttpStatus.OK);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<ResponseDTO> deleteById() {
        String cartModel = cartService.emptyCart();
        ResponseDTO responseDTO = new ResponseDTO("Remove all cart item successfully..!", cartModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.ACCEPTED);
    }


}