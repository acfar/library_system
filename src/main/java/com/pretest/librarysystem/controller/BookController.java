package com.pretest.librarysystem.controller;

import com.pretest.librarysystem.dto.BookRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody BookRequest req) {
        return ResponseEntity.ok(bookService.createBook(req));
    }

    @GetMapping
    public Page<Book> listBooks(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return bookService.listBooks(PageRequest.of(page, size));
    }
}
