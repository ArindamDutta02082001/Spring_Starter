package com.example.project.onlinelibrarywithsecurity;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OnlinelibrarymanagementWithSecurityApplication {



	public static void main(String[] args) {
		SpringApplication.run(OnlinelibrarymanagementWithSecurityApplication.class, args);
		System.out.println("Server started at 9000 .. . .");

		AdminService adminService1 = new AdminService();
		CreateAdminDto createAdminDto = CreateAdminDto.builder().name("Arindam").username("arindam").password(new BCryptPasswordEncoder().encode("arindam@123")).build();
		adminService1.createAdmin(createAdminDto);

	}


}
