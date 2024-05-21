package com.example.demospringsecurity.database.controllers;

import com.example.demospringsecurity.database.dto.createUserDto;
import com.example.demospringsecurity.database.entity.User;
import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ControllerInDB {


    //  these apis (/usersignup | ) will come before the authentication
    // to access this api no authentication should be present

    // signup - plain text from UI -> to store in encoded format in DB
    // login - plain text from UI -> encoded pwd -> check similarity

    // for any unsafe method like POST , PUT  , DELETE , PATCH we have to pass a csrf token to the server else csrf attack may occur.
    // for GET endpoint csrf token from frontend is not required . since we don`t have the frontend which will not generate a csrf token
    // we are disabling it here (very very UNSAFE method don`t do it)

    // Thus , the signup api can be tested from postman without the csrf
    // disabling csrf doesn`t mean that we don`t have to provide username and password in login

    // Thus ,  we have to pass either , the JSESSIONID ( authenticated session id ) or the correct username , pwd must be passes on hitting the below apis


    @Autowired
    UserService userService;


    /* general unsecured public apis , that is accessible to anyone i.e any authorized + unauthorized personnel */
    // general apis

    @GetMapping("/home")
    public String sayHello(){
        return "Welcome!!!";
    }

    @GetMapping("/shop")
    public String sayHelloShop(){
        return "Welcome to the Shop Page!!!";
    }


    @PostMapping("/usersignup")                         // kept unsecured general api
    public User signUp(@RequestBody createUserDto user)
    {
        User newuser = User.builder()
                .username(user.getUsername())
                .isAccountNonExpired(true)
                .isEnabled(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .authorities(user.getAuthorities())
                .password( new BCryptPasswordEncoder().encode(user.getPassword()))
                .build();
        this.userService.saveFromDB(newuser);
        return newuser;
    }

    // all the below apis will get authenticated first by the username and password
    // thus we need to store the authenticated JSESSIONID to authenticate
    // then they will be authorized based on the authorization level


    /* to register new user and get credential of a person ( student , faculty , admin) */
    // for admins


    // unsafe method , we later created the safe method to fetch details
    @GetMapping("/credential")
    public UserDetails getAllCredentials(@RequestParam("username") String username)
    {
        return userService.loadUserByUsername(username);
    }


    @GetMapping("/credential/all")
    public List<User> getAllUser()
    {
        return userService.getAllUsersFromDB();
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
    public User getMyDetail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PutMapping("/update-password")
    public User updateMyDetail(@RequestBody createUserDto createUserdto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        User newUser = (User) userService.loadUserByUsername(username);

        // updating the password
        newUser.setPassword(new BCryptPasswordEncoder().encode(createUserdto.getPassword()));
        userService.saveFromDB(newUser);
        return newUser;
    }

    @DeleteMapping("/delete-detail")
    public User deleteMyDetail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        userService.deleteUserFromDB(user.getId());
        return user;
    }



}
