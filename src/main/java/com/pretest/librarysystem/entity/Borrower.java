package com.pretest.librarysystem.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Borrower {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String borrowerUsername;

    @OneToMany(mappedBy = "borrower", cascade = CascadeType.ALL)
    private List<BorrowerBook> borrowerBooks = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrowerUsername() {
        return borrowerUsername;
    }

    public void setBorrowerUsername(String borrowerUsername) {
        this.borrowerUsername = borrowerUsername;
    }

    public List<BorrowerBook> getBorrowerBooks() {
        return borrowerBooks;
    }

    public void setBorrowerBooks(List<BorrowerBook> borrowerBooks) {
        this.borrowerBooks = borrowerBooks;
    }
}
