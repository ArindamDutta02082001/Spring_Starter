package com.example.demospringsecurity.controllers;

import com.example.demospringsecurity.config.SecurityconfigInMemory;
import com.example.demospringsecurity.repository.RepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ControllerInMemory {

    /*
        cannot do any post , put , patch etc except for get as on going to the
        other endpoint it will redirect you to the /login endpoint
     */


    @Autowired
    SecurityconfigInMemory securityconfigInMemory;

    /* general unsecured public apis , that is accessible to anyone i.e any authorized + unauthorized personnel */

    @GetMapping("/home")
    public String sayHello(){
        return "Welcome!!!";
    }

    @GetMapping("/shop")
    public String sayHelloShop(){
        return "Welcome to the Shop Page!!!";
    }

    /* to register new user and get credential of a person ( student , faculty , admin) */

    @PostMapping("/signup")
    public UserDetails userSignup(@RequestParam("username") String username  , @RequestParam("password") String password , @RequestParam("role") String role  )
    {
        UserDetails userDetails =  User.builder().username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .roles(role)
                .build();
        securityconfigInMemory.saveUserDetailsInDB(userDetails);
        return  userDetails;
    }

    @GetMapping("/credential")
    public UserDetails getAllCredentials(@RequestParam("username") String username)
    {
        return securityconfigInMemory.getUserDetailInDB(username);
    }


    /* below are the endpoints for authorized student , faculty , admin */
    // for faculty
    @GetMapping("/faculty")
    public String sayHelloToFaculty(){
        return "Hello faculty";
    }

    // for student
    @GetMapping("/student")
    public String sayHelloToStudent(){
        return "Hello student";
    }

    // for student / faculty
    @GetMapping("/library")
    public String welcomeToLibrary(){
        return "Welcome to library!!";
    }


}
