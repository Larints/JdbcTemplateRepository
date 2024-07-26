package com.example.bootjdbc.service;

import com.example.bootjdbc.model.Book;
import com.example.bootjdbc.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@AllArgsConstructor
@Service
public class BookStoreService implements BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAllBooks();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

}
