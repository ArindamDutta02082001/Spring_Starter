package com.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestapiSpringbootApplication {

	public static void main(String[] args) {

		SpringApplication.run(RestapiSpringbootApplication.class, args);

		System.out.println("Server started at 9000 ...");
	}

}
