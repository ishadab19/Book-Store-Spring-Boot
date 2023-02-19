package com.bridgelabz.bookstoreapp.controller;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.model.BookData;
import com.bridgelabz.bookstoreapp.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@CrossOrigin( allowedHeaders = "*", origins = "*")
@RequestMapping("/book")
@RestController
public class BookController {

    @Autowired
    private BookServiceImpl bookService;


    @GetMapping("/getAll")
    public ResponseEntity<ResponseDTO> getAllBookData() {
        List<BookData> listOfBooks = bookService.getAllBookData();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(responseDto, HttpStatus.OK);
    }

    @GetMapping("/get/{bookId}")
    public ResponseEntity<ResponseDTO> getBookModelById(@PathVariable int bookId) {
        BookData bookModel = bookService.getBookModelById(bookId);
        ResponseDTO responseDto = new ResponseDTO("Success Call for Book Id!!!", bookModel);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/searchByName/{name}")
    public ResponseEntity<ResponseDTO> searchByName(@PathVariable String name ) {
        List<BookData> bookDataDataList = bookService.searchByName(name);
        ResponseDTO respDTO = new ResponseDTO("Books are ....", bookDataDataList);
        return new ResponseEntity<>(respDTO, HttpStatus.OK);
    }

    /**
     * @Purpose : To show total Book Count in book store
     */
    @GetMapping("/totalBookCount")
    public ResponseEntity<ResponseDTO> getTotalBookCount() {
        int totalCount = bookService.getTotalBooksCount();
        return new ResponseEntity<>(new ResponseDTO("Total books are  : ", totalCount), HttpStatus.OK);
    }

    @PostMapping("/addBook")
    public ResponseEntity<ResponseDTO> addUserInBookStore(@RequestBody BookDTO bookDto) {
        BookData newBook = bookService.createBook(bookDto);
        ResponseDTO responseDto = new ResponseDTO("Created the new book in book store", newBook);
        return new ResponseEntity(responseDto, HttpStatus.CREATED);
    }

    @PutMapping("/updateBookById/{bookId}")
    public ResponseEntity<ResponseDTO> updateRecordById(@PathVariable int bookId, @Valid @RequestBody BookDTO bookDTO) {
        BookData updateRecord = bookService.updateRecordById(bookDTO,bookId);
        ResponseDTO responseDto = new ResponseDTO(" Book Record updated successfully by Id", updateRecord);
        return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteBook/{bookId}")
    public ResponseEntity<ResponseDTO> deleteRecordById(@PathVariable int bookId) {
        BookData bookModel = bookService.deleteBookRecord(bookId);
        ResponseDTO responseDto = new ResponseDTO("Record deleted successfully !", bookModel);
        return new ResponseEntity(responseDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/sortAsc")
    public ResponseEntity<ResponseDTO> getBooksInAscendingOrder() {
        List<BookData> listOfBooks = bookService.sortedListOfBooksInAscendingOrder();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @GetMapping("/sortDesc")
    public ResponseEntity<ResponseDTO> getBooksInDescendingOrder() {
        List<BookData> listOfBooks = bookService.sortedListOfBooksInDescendingOrder();
        ResponseDTO responseDto = new ResponseDTO("Data retrieved successfully :", listOfBooks);
        return new ResponseEntity(responseDto, HttpStatus.OK);

    }
}