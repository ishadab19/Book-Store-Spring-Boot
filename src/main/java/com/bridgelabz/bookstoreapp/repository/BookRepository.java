package com.bridgelabz.bookstoreapp.repository;
import com.bridgelabz.bookstoreapp.model.BookData;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<BookData, Integer> {

}
