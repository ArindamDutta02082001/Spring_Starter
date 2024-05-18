package com.example.project.onlinelibrarywithsecurity.repository;

import com.example.project.onlinelibrarywithsecurity.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
