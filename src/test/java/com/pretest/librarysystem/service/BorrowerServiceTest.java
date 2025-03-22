package com.pretest.librarysystem.service;


import com.pretest.librarysystem.config.exception.BusinessException;
import com.pretest.librarysystem.dto.BorrowerRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.entity.Borrower;
import com.pretest.librarysystem.repository.BookRepository;
import com.pretest.librarysystem.repository.BorrowerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BorrowerServiceTest {

    @InjectMocks
    private BorrowerService borrowerService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowerRepository borrowerRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    private BorrowerRequest createRequest() {
        return new BorrowerRequest(
                "john_doe",
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 15),
                "book123"
        );
    }

    @Test
    void testCreateBorrower_WithNewBorrower() {
        BorrowerRequest request = createRequest();

        Book book = new Book();
        book.setId("book123");

        when(bookRepository.findById("book123")).thenReturn(Optional.of(book));
        when(borrowerRepository.findBorrowerByBorrowerUsername("john_doe")).thenReturn(Optional.empty());
        when(borrowerRepository.save(any(Borrower.class))).thenAnswer(invocation -> invocation.getArgument(0));

        borrowerService.createBorrower(request);

        verify(bookRepository).findById("book123");
        verify(borrowerRepository).findBorrowerByBorrowerUsername("john_doe");
        verify(borrowerRepository).save(any(Borrower.class));
    }

    @Test
    void testCreateBorrower_WithExistingBorrower() {
        BorrowerRequest request = createRequest();

        Book book = new Book();
        book.setId("book123");

        Borrower borrower = new Borrower();
        borrower.setId("borrower1");
        borrower.setBorrowerUsername("john_doe");

        when(bookRepository.findById("book123")).thenReturn(Optional.of(book));
        when(borrowerRepository.findBorrowerByBorrowerUsername("john_doe")).thenReturn(Optional.of(borrower));
        when(borrowerRepository.save(any(Borrower.class))).thenAnswer(invocation -> invocation.getArgument(0));

        borrowerService.createBorrower(request);

        verify(bookRepository).findById("book123");
        verify(borrowerRepository).findBorrowerByBorrowerUsername("john_doe");
        verify(borrowerRepository).save(any(Borrower.class));
    }

    @Test
    void testCreateBorrower_BookNotFound() {
        BorrowerRequest request = createRequest();
        when(bookRepository.findById("book123")).thenReturn(Optional.empty());

        BusinessException ex = assertThrows(BusinessException.class, () ->
                borrowerService.createBorrower(request));

        assertEquals("Book can't be found in our system", ex.getMessage());
        verify(bookRepository).findById("book123");
        verify(borrowerRepository, never()).findBorrowerByBorrowerUsername(any());
        verify(borrowerRepository, never()).save(any());
    }
}
