package com.example.demospringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DemospringsecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemospringsecurityApplication.class, args);
		System.out.println("server started in 9000 ...");
	}

}
