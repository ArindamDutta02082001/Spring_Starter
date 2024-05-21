package com.example.demospringsecurity.controllers;

import com.example.demospringsecurity.config.SecurityconfigInMemory;
import com.example.demospringsecurity.dto.createUserDto;
import com.example.demospringsecurity.models.DemoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class ControllerInMemory {


    // signup - plain text from UI -> to store in encoded format in DB
    // login - plain text from UI -> encoded pwd -> check similarity

    // for any unsafe method like POST , PUT  , DELETE , PATCH we have to pass a csrf token to the server else csrf attack may occur.
    // for GET endpoint csrf token from frontend is not required . since we don`t have the frontend which will not generate a csrf token
    // we are disabling it here (very very UNSAFE method don`t do it)

    // Thus , the signup api can be tested from postman without the csrf
    // disabling csrf doesn`t mean that we don`t have to provide username and password in login

    // Thus ,  we have to pass either , the JSESSIONID ( authenticated session id ) or the correct username , pwd must be passes on hitting the below apis


    @Autowired
    SecurityconfigInMemory securityconfigInMemory;



    /* general unsecured public apis , that is accessible to any authorized / unauthorized personnel */
    // general apis

    @GetMapping("/home")
    public String sayHello(){
        return "Welcome!!!";
    }

    @GetMapping("/shop")
    public String sayHelloShop(){
        return "Welcome to the Shop Page!!!";
    }




    // all the below apis will get authenticated first by the username and password
    // thus we need to store the authenticated JSESSIONID to authenticate
    // then they will be authorized based on the authorization level

    /* to register new user and get credential of a person ( student , faculty , admin) */
    // for admins

    @PostMapping("/signup")
    public DemoUser userSignup(@RequestBody createUserDto createUserdto )
    {
        DemoUser user =  DemoUser.builder()
                .username(createUserdto.getUsername())
                .password(new BCryptPasswordEncoder().encode(createUserdto.getPassword()))
                .authorities(createUserdto.getAuthorities())
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .build();
        securityconfigInMemory.saveUserDetailsInDB(user);
        return  user;
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


    /* Spring Security Context
     *  just like IOC container / application context , it is also a container that holds the object details
     *
     */


    // try to get a particular credential of a user
    // how to do ? ans : we have to pass the user_id either in req param , body or in path variable
    // but user_id is sensitive information , so we should not pass them like this .
    // here comes the spring security context , which holds the user object details that is currently logged in

    @GetMapping("/my-detail")
    public UserDetails getMyDetail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetails)authentication.getPrincipal();

    }


}