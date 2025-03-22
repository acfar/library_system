package com.pretest.librarysystem.service;

import com.pretest.librarysystem.config.exception.BusinessException;
import com.pretest.librarysystem.dto.BorrowerRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.entity.Borrower;
import com.pretest.librarysystem.entity.BorrowerBook;
import com.pretest.librarysystem.repository.BookRepository;
import com.pretest.librarysystem.repository.BorrowerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BorrowerService {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowerRepository borrowerRepository;

    public void createBorrower(BorrowerRequest req) {
        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new BusinessException(
                        HttpStatus.BAD_REQUEST, "FAILED", "Book can't be found in our system"));

        // Fetch or create a new borrowing for the borrower
        Borrower borrower = borrowerRepository.findBorrowerByBorrowerUsername(req.getBorrowerUsername())
                .orElseGet(() -> {
                    Borrower newBorrower = new Borrower();
                    newBorrower.setBorrowerUsername(req.getBorrowerUsername());
                    return newBorrower;
                });

        BorrowerBook borrowRecord = new BorrowerBook();
        borrowRecord.setBorrower(borrower);
        borrowRecord.setBook(book);
        borrowRecord.setBorrowDate(req.getBorrowDate());
        borrowRecord.setReturnDate(req.getReturnDate());

        // Add the new book to the borrowing
        borrower.getBorrowerBooks().add(borrowRecord);

        borrowerRepository.save(borrower);
    }
}
