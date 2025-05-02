package com.javatechie;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringTransactionExampleApplication {

	static String  serverPort ;
	@Value("${server.port}")
	public void setServerPort( String value )
	{
		this.serverPort  = value;
	}


	public static void main(String[] args) {
		SpringApplication.run(SpringTransactionExampleApplication.class, args);
		System.out.println("Server started on port " + serverPort);
	}

//	@PostConstruct
//	public void printServerPort() {
//		System.out.println("Server started on port " + serverPort);
//	}

}
