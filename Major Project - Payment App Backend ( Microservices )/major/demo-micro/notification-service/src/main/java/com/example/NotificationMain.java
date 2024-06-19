package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationMain {
    public static void main(String[] args) {
        SpringApplication.run(NotificationMain.class);
        System.out.println("Notification service running in 3000 ...");
    }
}