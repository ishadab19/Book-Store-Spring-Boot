package com.bridgelabz.bookstoreapp.service;
import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.exception.BookStoreException;
import com.bridgelabz.bookstoreapp.model.BookData;
import com.bridgelabz.bookstoreapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService implements BookServiceImpl {


    @Autowired
    private BookRepository bookRepo;


    @Override
    public BookData createBook(BookDTO bookDto) {
        BookData book = new BookData(bookDto);
        bookRepo.save(book);
        return book;
    }

    @Override
    public List<BookData> getAllBookData() {
        List<BookData> getBooks=bookRepo.findAll();
        return getBooks;
    }

    @Override
    public BookData updateRecordById(BookDTO bookDto, int id) {
        Optional<BookData> book = bookRepo.findById(id);
        book.get().setAuthorName(bookDto.getAuthorName());
        book.get().setBookDescription(bookDto.getBookDescription());
        book.get().setBookImg(bookDto.getBookImg());
        book.get().setBookName(bookDto.getBookName());
        book.get().setPrice(bookDto.getPrice());
        book.get().setQuantity(bookDto.getQuantity());
        bookRepo.save(book.get());
        return book.get();
    }



    @Override
    public BookData deleteBookRecord(int bookId) {
        Optional<BookData> book = bookRepo.findById(bookId);
        if (book.isPresent()) {
            bookRepo.deleteById(bookId);
            return book.get();

        } else {
            return null;
        }
    }
    @Override
    public BookData getBookModelById(int bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(() -> new BookStoreException("Book not found In the List"));
    }
    @Override
    public List<BookData> sortedListOfBooksInAscendingOrder() {
        List<BookData> getSortedList=  bookRepo.findAll(Sort.by(Sort.Direction.ASC,"price"));
        return getSortedList;
    }

    @Override
    public List<BookData> sortedListOfBooksInDescendingOrder() {
        List<BookData> getSortedListInDesc = bookRepo.findAll(Sort.by(Sort.Direction.DESC,"price"));
        return getSortedListInDesc;

    }

    @Override
    public int getTotalBooksCount() {
        return bookRepo.findAll().size();
    }

    @Override
    public List<BookData> searchByName(String name) {
        String name1 = name.toLowerCase();
        List<BookData> bookData = getAllBookData();
        List<BookData> collect = bookData.stream()
                .filter(bookDataData -> bookDataData.getBookName().toLowerCase().contains(name1))
                .collect(Collectors.toList());

        return collect;
    }


    public BookData updateBookQuantity(int bookId, int quantity) {
        BookData editbook = bookRepo.findById(bookId).orElse(null);
        if (editbook != null) {
            editbook.setQuantity(quantity);
            return bookRepo.save(editbook);
        }
        return null;
    }
}