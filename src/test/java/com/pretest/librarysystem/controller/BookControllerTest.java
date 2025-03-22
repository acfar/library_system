package com.pretest.librarysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pretest.librarysystem.dto.BookRequest;
import com.pretest.librarysystem.entity.Book;
import com.pretest.librarysystem.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private BookRequest request;
    private Book book;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        request = new BookRequest();
        request.setTitle("Domain-Driven Design");
        request.setAuthor("Eric Evans");
        request.setPublicationYear(2003);
        request.setIsbn("9780321125217");

        book = new Book();
        book.setId("book-uuid-1");
        book.setTitle(request.getTitle());
        book.setAuthor(request.getAuthor());
        book.setPublicationYear(request.getPublicationYear());
        book.setIsbn(request.getIsbn());
    }

    @Test
    void testAddBook() throws Exception {
        Mockito.when(bookService.createBook(any(BookRequest.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(request.getTitle()))
                .andExpect(jsonPath("$.author").value(request.getAuthor()))
                .andExpect(jsonPath("$.publicationYear").value(request.getPublicationYear()))
                .andExpect(jsonPath("$.isbn").value(request.getIsbn()));
    }

    @Test
    void testListBooks() throws Exception {
        List<Book> books = List.of(book);
        Page<Book> bookPage = new PageImpl<>(books);

        Mockito.when(bookService.listBooks(PageRequest.of(0, 10))).thenReturn(bookPage);

        mockMvc.perform(get("/books?page=0&size=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value(book.getTitle()))
                .andExpect(jsonPath("$.content[0].author").value(book.getAuthor()))
                .andExpect(jsonPath("$.content[0].publicationYear").value(book.getPublicationYear()))
                .andExpect(jsonPath("$.content[0].isbn").value(book.getIsbn()));
    }
}
