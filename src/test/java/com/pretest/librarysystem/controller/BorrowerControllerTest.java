package com.pretest.librarysystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pretest.librarysystem.dto.BorrowerRequest;
import com.pretest.librarysystem.service.BorrowerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BorrowerController.class)
class BorrowerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BorrowerService borrowingService;

    private BorrowerRequest borrowerRequest;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // 👈 Register Java Time support

        borrowerRequest = new BorrowerRequest(
                "john_doe",
                LocalDate.of(2024, 3, 20),
                LocalDate.of(2024, 3, 27),
                "book123"
        );
    }

    @Test
    void testBorrowBook_ReturnsCreatedStatus() throws Exception {
        Mockito.doNothing().when(borrowingService).createBorrower(any(BorrowerRequest.class));

        mockMvc.perform(post("/borrower")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(borrowerRequest)))
                .andExpect(status().isCreated());
    }
}
