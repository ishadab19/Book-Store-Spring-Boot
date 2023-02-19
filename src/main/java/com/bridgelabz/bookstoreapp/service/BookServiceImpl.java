package com.bridgelabz.bookstoreapp.service;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.model.BookData;

import java.util.List;

public interface BookServiceImpl {

    BookData createBook(BookDTO bookDTO);

    List<BookData> getAllBookData();

    BookData updateRecordById(BookDTO bookDTO, int bookId);



    BookData deleteBookRecord(int bookId);

    BookData getBookModelById(int bookId);

    List<BookData> sortedListOfBooksInAscendingOrder();

    List<BookData> sortedListOfBooksInDescendingOrder();


    int getTotalBooksCount();

    List<BookData> searchByName(String name);
}