package com.project.transaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TransactionMserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionMserviceApplication.class, args);
		System.out.println("Transaction service started at 7000 ...");
	}

}
