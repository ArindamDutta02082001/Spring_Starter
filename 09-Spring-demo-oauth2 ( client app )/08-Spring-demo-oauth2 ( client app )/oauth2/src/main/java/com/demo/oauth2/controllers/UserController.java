package com.demo.oauth2.controllers;

import com.demo.oauth2.dto.createUserDto;
import com.demo.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/user")
    public String getUser(){
//    public Map<String, Object> getUser(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
//
//
//        return oAuth2User.getAttributes();
return  "user";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody createUserDto request)
    {
          userService.createUser(request);

          return "user created";
    }

}
