package com.example.demospringsecurity.database.controllers;

import com.example.demospringsecurity.database.entity.User;
import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ControllerInDB {
    @Autowired
    UserService userService;

    @PostMapping("/user")
    public User setUser(@RequestBody User user)
    {
       User newuser = User.builder()
                .username(user.getUsername())
                .isAccountNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .authorities(user.getAuthorities().toString())
                .password(user.getPassword())
                .build();
        this.userService.save(newuser);
        return newuser;
    }
    @GetMapping("/home")
    public String sayHello(){                 // anyone
        return "Welcome!!!";
    }

    @GetMapping("/faculty")                   // faculty
    public String sayHelloToFaculty(){
        return "Hello faculty";
    }

    @GetMapping("/student")                    // student
    public String sayHelloToStudent(){
        return "Hello student";
    }

    @GetMapping("/library")                   // student / faculty
    public String welcomeToLibrary(){
        return "Welcome to library!!";
    }


}
