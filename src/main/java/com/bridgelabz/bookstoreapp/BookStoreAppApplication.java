package com.bridgelabz.bookstoreapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@Slf4j
public class BookStoreAppApplication {

    public static void main(String[] args) {
        System.out.println("Book Store Application started !");

        ApplicationContext context = SpringApplication.run(BookStoreAppApplication.class, args);

        log.info("Book Store Application started in {} Environment",
                context.getEnvironment().getProperty("environment"));
        log.info("Book Store Application DB User is {} ",
                context.getEnvironment().getProperty("spring.datasource.username"));
    }
}
