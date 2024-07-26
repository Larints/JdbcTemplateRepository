package com.example.bootjdbc.controller;

import com.example.bootjdbc.model.Book;
import com.example.bootjdbc.service.BookStore;
import com.example.bootjdbc.service.BookStoreService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/v1/bookstore")
public class BookStoreController {

    private final BookStore bookStoreService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookStoreService.findAllBooks();
        return ResponseEntity.ok(books);
    }

    @PostMapping
    public ResponseEntity<Book> save(@ RequestBody Book book) {
        Book savedBook = bookStoreService.save(book);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Book book = bookStoreService.findById(id);
        return ResponseEntity.ok(book);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@ RequestBody Book book) {
        Book updatedBook = bookStoreService.updateBook(book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookStoreService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
