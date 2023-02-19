package com.bridgelabz.bookstoreapp.exception;

public class BookStoreException extends RuntimeException {



    public enum ExceptionType {
        EMAIL_NOT_FOUND,
        PASSWORD_INVALID,
        USER_ALREADY_PRESENT,
        BOOK_ALREADY_PRESENT,

        USER_NOT_FOUND,

    }

    public BookStoreException.ExceptionType type;


    public BookStoreException(String message, BookStoreException.ExceptionType type) {
        super(message);
        this.type = type;
    }

    public BookStoreException(String message) {
        super(message);
    }
}
