package com.pretest.librarysystem.dto;

import java.time.LocalDate;


public class BorrowerRequest {
    private String borrowerUsername;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private String bookId;

    public BorrowerRequest(String borrowerUsername, LocalDate borrowDate, LocalDate returnDate, String bookId) {
        this.borrowerUsername = borrowerUsername;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.bookId = bookId;
    }

    public String getBorrowerUsername() {
        return borrowerUsername;
    }

    public void setBorrowerUsername(String borrowerUsername) {
        this.borrowerUsername = borrowerUsername;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
