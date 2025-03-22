package com.pretest.librarysystem.repository;

import com.pretest.librarysystem.entity.Borrower;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BorrowerRepository extends JpaRepository <Borrower, String>{
    Optional<Borrower> findBorrowerByBorrowerUsername(String username);
}
