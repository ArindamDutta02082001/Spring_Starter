package com.example.project.onlinelibrarywithsecurity.controller;

import com.example.project.onlinelibrarywithsecurity.dto.CreateAdminDto;
import com.example.project.onlinelibrarywithsecurity.models.Admin;
import com.example.project.onlinelibrarywithsecurity.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @PostMapping("")
    public Admin createAdmin(@RequestBody @Valid CreateAdminDto createAdminDto){
        return adminService.createAdmin(createAdminDto);
    }

}
