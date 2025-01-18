package com.example.demospringsecurity.database;

import com.example.demospringsecurity.database.config.SecurityconfigInDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoSecurityApplication {

	@Autowired
	SecurityconfigInDB securityconfigInDB;

	public static void main(String[] args) {
		SpringApplication.run(DemoSecurityApplication.class, args);
		System.out.println("Server started at 9000 ...");
	}

}
