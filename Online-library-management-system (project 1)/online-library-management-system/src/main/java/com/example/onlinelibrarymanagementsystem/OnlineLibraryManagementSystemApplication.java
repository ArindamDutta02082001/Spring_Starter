package com.example.onlinelibrarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class OnlineLibraryManagementSystemApplication {

	public static void main(String[] args) {

		ApplicationContext apc = SpringApplication.run(OnlineLibraryManagementSystemApplication.class, args);
		System.out.println("Server started at 9000 .. . .");
	}

}
