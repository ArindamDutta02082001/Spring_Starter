package com.project.user.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.user.dto.request.createUserDto;
import com.project.user.dto.response.userCacheResponseDto;
import com.project.user.models.User;
import com.project.user.service.UserService;
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
