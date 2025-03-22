package com.pretest.librarysystem.service;

import com.pretest.librarysystem.config.exception.BusinessException;
import com.pretest.librarysystem.dto.BookRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private BookRequest createSampleRequest() {
        BookRequest req = new BookRequest();
        req.setTitle("Clean Code");
        req.setAuthor("Robert C. Martin");
        req.setPublicationYear(2008);
        req.setIsbn("9780132350884");
        return req;
    }

    @Test
    void testCreateBook_Success() {
        BookRequest request = createSampleRequest();
        when(bookRepository.findBookByIsbn(request.getIsbn())).thenReturn(Optional.empty());

        Book savedBook = new Book();
        savedBook.setTitle(request.getTitle());
        savedBook.setAuthor(request.getAuthor());
        savedBook.setPublicationYear(request.getPublicationYear());
        savedBook.setIsbn(request.getIsbn());

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        Book result = bookService.createBook(request);

        assertNotNull(result);
        assertEquals(request.getTitle(), result.getTitle());
        verify(bookRepository).findBookByIsbn(request.getIsbn());
        verify(bookRepository).save(any(Book.class));
    }

    @Test
    void testCreateBook_AlreadyExists() {
        BookRequest request = createSampleRequest();
        Book existingBook = new Book();
        existingBook.setIsbn(request.getIsbn());

        when(bookRepository.findBookByIsbn(request.getIsbn())).thenReturn(Optional.of(existingBook));

        BusinessException ex = assertThrows(BusinessException.class, () -> {
            bookService.createBook(request);
        });

        assertEquals("Book already recorded in our system", ex.getMessage());
        verify(bookRepository, times(1)).findBookByIsbn(request.getIsbn());
        verify(bookRepository, never()).save(any(Book.class));
    }

    @Test
    void testListBooks() {
        Pageable pageable = PageRequest.of(0, 10);

        Book savedBook1 = new Book();
        savedBook1.setTitle("Title1");
        savedBook1.setAuthor("Author1");
        savedBook1.setPublicationYear(2020);
        savedBook1.setIsbn("ISBN1");
        Book savedBook2 = new Book();
        savedBook2.setTitle("Title2");
        savedBook2.setAuthor("Author2");
        savedBook2.setPublicationYear(2020);
        savedBook2.setIsbn("ISBN2");

        List<Book> books = Arrays.asList(savedBook1, savedBook2);
        Page<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.listBooks(pageable);

        assertEquals(2, result.getContent().size());
        verify(bookRepository).findAll(pageable);
    }
}
