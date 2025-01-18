package com.project.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
		System.out.println("Gateway service started in 8001 ...");
	}

}
