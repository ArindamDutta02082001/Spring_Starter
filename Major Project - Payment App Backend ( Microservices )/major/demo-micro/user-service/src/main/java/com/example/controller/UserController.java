package com.example.controller;

import com.example.dto.request.createUserDto;
import com.example.dto.response.userCacheResponseDto;
import com.example.models.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/new")
    public User createUser(@RequestBody @Valid createUserDto userCreateRequest) throws JsonProcessingException {
        return this.userService.create(userCreateRequest);
    }

    // This is called by anyone who is a user
    @GetMapping("/details")
    public userCacheResponseDto getUserDetailsByUserID(@RequestParam("id") int userId){
        System.out.println("called");
        return this.userService.getUserByUserId(userId);
    }

    // This is called by transaction service or in future any other internal service but not a user
    @GetMapping("/mobile/{mobile-number}")
    public User getUserDetailsByMobile(@PathVariable("mobile-number") String mobile){
        return (User) this.userService.loadUserByUsername(mobile);
    }

}
