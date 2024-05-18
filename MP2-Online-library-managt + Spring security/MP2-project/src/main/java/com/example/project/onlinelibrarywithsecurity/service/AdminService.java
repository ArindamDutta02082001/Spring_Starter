package com.example.project.onlinelibrarywithsecurity.service;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.models.Admin;
import com.example.project.onlinelibrarywithsecurity.models.SecuredUser;
import com.example.project.onlinelibrarywithsecurity.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    SecuredUserService securedUserService;

    @Autowired
    AdminRepository adminRepository;

    // before creating a admin , SecuredUser should have a new row
    public Admin createAdmin(CreateAdminDto createAdminDto){

        SecuredUser securedUser = SecuredUser.builder()
                .authorities("admin")
                .username(createAdminDto.getUsername())
                .password(new BCryptPasswordEncoder().encode(createAdminDto.getPassword()))
                .build();
        Admin admin = Admin.builder()
                .name(createAdminDto.getName())
                .securedUser(securedUser)
                .build();

        // create a secured user
        securedUser.setAdmin(admin);
        securedUserService.saveUserinDB(securedUser);

        // create a admin
        admin.setSecuredUser(securedUser);
        return adminRepository.save(admin);
    }
}
