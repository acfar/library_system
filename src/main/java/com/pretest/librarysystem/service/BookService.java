package com.pretest.librarysystem.service;


import com.pretest.librarysystem.config.exception.BusinessException;
import com.pretest.librarysystem.dto.BookRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book createBook(BookRequest req) {
        Optional<Book> bookOptional = bookRepository.findBookByIsbn(req.getIsbn());
        if (bookOptional.isPresent()){
            throw new BusinessException(
                    HttpStatus.BAD_REQUEST, "FAILED", "Book already recorded in our system");
        }

        Book book = new Book();
        book.setAuthor(req.getAuthor());
        book.setTitle(req.getTitle());
        book.setPublicationYear(req.getPublicationYear());
        book.setIsbn(req.getIsbn());
        return bookRepository.save(book);
    }

    public Page<Book> listBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
