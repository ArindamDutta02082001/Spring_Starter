package com.example.project.onlinelibrarywithsecurity.controller;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.models.Admin;
import com.example.project.onlinelibrarywithsecurity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("/new")
    public Admin createAdmin(@RequestBody @Valid CreateAdminDto createAdminDto){
        return adminService.createAdmin(createAdminDto);
    }

    // get all admin infos
    @GetMapping("/getall")
    public List<Admin> getAllAdmins()
    {
        return adminService.getAllAdmin();
    }


    // get only current admin info
    @GetMapping("/get")
    public Object getAdminInfo()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return  authentication.getPrincipal();
    }

}
