package com.example.bootjdbc.service;

import com.example.bootjdbc.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BookStore {

    public List<Book> findAllBooks();

    public Book findById(Long id);

    public Book save(Book book);

    public Book updateBook(Book book);

    public void deleteById(Long id);

}
