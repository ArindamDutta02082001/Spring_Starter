package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionMain {
    public static void main(String[] args) {
        SpringApplication.run(TransactionMain.class);
        System.out.println("Transaction service started at 7000 ...");
    }
}