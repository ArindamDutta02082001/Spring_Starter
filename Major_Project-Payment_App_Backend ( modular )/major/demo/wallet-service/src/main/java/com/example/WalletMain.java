package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletMain {
    public static void main(String[] args) {
        SpringApplication.run(WalletMain.class);
        System.out.println("User service started in 5000 port ");
    }
}