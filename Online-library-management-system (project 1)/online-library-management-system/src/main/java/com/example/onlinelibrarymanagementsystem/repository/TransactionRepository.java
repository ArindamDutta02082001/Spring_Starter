package com.example.onlinelibrarymanagementsystem.repository;

import com.example.onlinelibrarymanagementsystem.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
