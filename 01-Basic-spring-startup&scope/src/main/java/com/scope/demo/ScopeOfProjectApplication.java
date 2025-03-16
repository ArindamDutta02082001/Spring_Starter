package com.scope.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScopeOfProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScopeOfProjectApplication.class, args);

		System.out.println("Server started");
	}

}
