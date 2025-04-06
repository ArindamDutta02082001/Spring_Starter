package com.example.project.onlinelibrarywithsecurity.service.securityService;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.models.securitymodels.Admin;
import com.example.project.onlinelibrarywithsecurity.models.securitymodels.SecuredUser;
import com.example.project.onlinelibrarywithsecurity.repository.securityRepository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();

        // create a secured user
        securedUserService.saveUserinDB(securedUser);

        // create an admin
        Admin admin = Admin.builder()
                .name(createAdminDto.getName())
                .securedUser(securedUser)
                .securedUser(securedUser)
                .build();

        return adminRepository.save(admin);
    }

    // getting all the admins
    public List<Admin> getAllAdmin()
    {
        return adminRepository.findAll();
    }
}
