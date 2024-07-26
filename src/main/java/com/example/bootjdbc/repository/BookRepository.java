package com.example.bootjdbc.repository;

import com.example.bootjdbc.model.Book;

import java.util.List;

public interface BookRepository {

    public List<Book> findAllBooks();

    public Book findById(Long id);

    public Book save(Book book);

    public Book updateBook(Book book);

    public void deleteById(Long id);
}
