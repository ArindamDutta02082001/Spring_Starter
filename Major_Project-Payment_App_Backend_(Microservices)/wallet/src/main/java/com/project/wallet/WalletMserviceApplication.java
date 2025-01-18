package com.project.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WalletMserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletMserviceApplication.class, args);
		System.out.println("User service started in 5000 port ");
	}

}
