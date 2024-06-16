package com.demo.oauth2.controllers;

import com.demo.oauth2.dto.changePasswordDto;
import com.demo.oauth2.dto.responseDto;
import com.demo.oauth2.models.DemoUser;
import com.demo.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

    @Autowired
    UserService userService;



    @GetMapping("/")
    public String home()
    {
        return "welcome home";
    }

    @GetMapping("/current-user-profile")
    public Object getUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return new responseDto("","","");
    }

    @PostMapping("/change-password")
    public Object changePassword(@RequestBody changePasswordDto changePasswordDto)
    {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.changePassword(changePasswordDto.getNewPassword(), changePasswordDto.getConfirmNewPassword(), userDetails.getUsername());
    }



}
