package com.example.demospringsecurity.controllers;

import com.example.demospringsecurity.dto.createUserDto;
import com.example.demospringsecurity.models.DemoUser;
import com.example.demospringsecurity.service.InmemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
public class ControllerInMemory {


    @Autowired
    InmemoryService inmemoryService;

    // default endpoint where you get redirected on successfully login
    @GetMapping("/")
    public String defaultEndpoint()
    {
        return "authenticated";
    }

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


    /** all the below apis will get authenticated first by the username and password
     thus we need to authenticate them based on username and password
     then they will be authorized based on the authorization level
     */


    /** below are the endpoints for authorized student , faculty , admin */

    // for admins
    @PostMapping("/signup")
    public UserDetails userSignup(@RequestBody createUserDto createUserdto )
    {
        // mapping of the user dto from user to a UserDetails object for Spring security
        DemoUser user =  DemoUser.builder()
                .username(createUserdto.getUsername())
                .password(new BCryptPasswordEncoder().encode(createUserdto.getPassword()))
                .authorities(createUserdto.getAuthorities())
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isAccountNonExpired(true)
                .isCredentialsNonExpired(true)
                .build();
        inmemoryService.saveUserDetails(user);
        return  user;
    }

    @GetMapping("/credential")
    public UserDetails getAllCredentials(@RequestParam("username") String username)
    {
        return inmemoryService.getUserDetails(username);
    }


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


    /**
     Spring Security Context
     just like IOC container / application context , it is also a container that holds the object details

     try to get a particular credential of a user
     how to do ? ans : we have to pass the user_id either in req param , body or in path variable
     but user_id is sensitive information , so we should not pass them like this .
     here comes the spring security context , which holds the user object details that is currently logged in

     */
    @GetMapping("/my-detail")
    public UserDetails getMyDetail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (UserDetails)authentication.getPrincipal();

    }


}