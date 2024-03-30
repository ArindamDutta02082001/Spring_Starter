package com.springboot;

import com.springboot.repository.DBConnection;
import com.springboot.repository.EmployeeRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class RestapiSpringbootApplication {

	public static void main(String[] args) throws  Exception {

		SpringApplication.run(RestapiSpringbootApplication.class, args);
		System.out.println("Server started at 9000...");

		EmployeeRepository.createEmployeeTable();
	}

}
