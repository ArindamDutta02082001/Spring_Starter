package com.example.demospringsecurity.controllers;

import com.example.demospringsecurity.config.SecurityconfigInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


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
    public UserDetails userSignup(@RequestParam("username") String username  , @RequestParam("password") String password , @RequestParam("role") String role  )
    {
        UserDetails userDetails =  User.builder().username(username)
                .password(new BCryptPasswordEncoder().encode(password))
                .authorities(role)
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
