package com.example.project.onlinelibrarywithsecurity;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class OnlinelibrarymanagementWithSecurityApplication {

	// the above method is used as we cant use the static
	private static AdminService adminService;

	@Autowired
	private AdminService adminServiceAuto;


	@PostConstruct
	private void init() {
		adminService = this.adminServiceAuto;
	}




	public static void main(String[] args) {
		SpringApplication.run(OnlinelibrarymanagementWithSecurityApplication.class, args);
		System.out.println("Server started at 9000 .. . .");


		CreateAdminDto createAdminDto = CreateAdminDto.builder().name("Arindam").username("arindam").password("arindam@123").build();
		adminService.createAdmin(createAdminDto);

	}


}
