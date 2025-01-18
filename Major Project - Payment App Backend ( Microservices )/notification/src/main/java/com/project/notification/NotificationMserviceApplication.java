package com.project.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationMserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationMserviceApplication.class, args);
		System.out.println("Notification service running in 3000 ...");
	}

}
