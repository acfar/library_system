package com.pretest.librarysystem.controller;

import com.pretest.librarysystem.dto.BorrowerRequest;
import com.pretest.librarysystem.service.BorrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/borrower")
public class BorrowerController {
    @Autowired
    private BorrowerService borrowingService;

    @PostMapping
    public ResponseEntity<Void> borrowBook(@RequestBody BorrowerRequest req) {
        borrowingService.createBorrower(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
