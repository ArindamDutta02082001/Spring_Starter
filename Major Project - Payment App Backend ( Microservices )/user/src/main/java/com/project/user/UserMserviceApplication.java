package com.project.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserMserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserMserviceApplication.class, args);
		System.out.println("User service started in 4000 port ");
	}

}
