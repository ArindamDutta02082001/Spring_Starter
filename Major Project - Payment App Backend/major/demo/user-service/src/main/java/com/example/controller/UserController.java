package com.example.controller;

import com.example.dto.createUserDto;
import com.example.models.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public void createUser(@RequestBody @Valid createUserDto userCreateRequest) throws JsonProcessingException {
        this.userService.create(userCreateRequest);
    }

    // This is called by anyone who is a user
    @GetMapping("/details")
    public User getUserDetails(@RequestParam("userId") int userId){
        return this.userService.get(userId);
    }

    // This is called by transaction service or in future any other internal service but not a user
    @GetMapping("/mobile/{mobileId}")
    public User getUserDetails(@PathVariable("mobileId") String mobile){
        return (User) this.userService.loadUserByUsername(mobile);
    }

}
