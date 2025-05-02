package com.example.project.onlinelibrarywithsecurity.repository.libraryRepository;

import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
}
