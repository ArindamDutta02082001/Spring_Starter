package com.example.demospringsecurity.database.controllers;

import com.example.demospringsecurity.database.dto.SignUpUserDto;
import com.example.demospringsecurity.database.model.User;
import com.example.demospringsecurity.database.security.SecurityDto.LoginDto;
import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ControllerInDB {

    @Autowired
    UserService userService;


    // endpoints if we want to disable the default query param and filter wla JSON body capture
    // you can include the /login method and here itself check by AuthenticationManager to authenticate

//    @Autowired
//    AuthenticationManager authenticationManager;
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> authRequest) {
//        String username = authRequest.get("username");
//        String password = authRequest.get("password");
//
//        // Authenticate the user using AuthenticationManager
//        Authentication authentication = authenticateUser(username, password);
//
//        // If authentication succeeds, you can load user details if needed
//        UserDetails userDetails = userService.loadUserByUsername(username);
//
//        // You can add custom logic (logging, security auditing, etc.) here
//        return "Login successful for user: " + username;
//    }
//
//    private Authentication authenticateUser(String username, String password) {
//        try {
//            // Create authentication token
//            UsernamePasswordAuthenticationToken authenticationToken =
//                    new UsernamePasswordAuthenticationToken(username, password);
//
//            // Use AuthenticationManager to authenticate the token
//            return authenticationManager.authenticate(authenticationToken);
//        } catch (BadCredentialsException e) {
//            throw new RuntimeException("Authentication failed: Bad credentials");
//        }
//    }

    // default endpoint where you get redirected on successfully login
    @GetMapping("/")
    public String defaultEndpoint()
    {
        return "authenticated";
    }

    /** general unsecured public apis , that is accessible to anyone i.e any authorized + unauthorized personnel */

    @GetMapping("/home")
    public String sayHello(){
        return "Welcome!!!";
    }

    @GetMapping("/shop")
    public String sayHelloShop(){
        return "Welcome to the Shop Page!!!";
    }

    // kept unsecured general api
    @PostMapping("/usersignup")
    public User signUp(@RequestBody SignUpUserDto user)
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

    /** all the below apis will get authenticated first by the username and password
     thus we need to authenticate them based on username and password
     then they will be authorized based on the authorization level
     */


    /** below are the endpoints for authorized student , faculty , admin */

    // for admins
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

    // for any logged in user
    @GetMapping("/my-detail")
    public User getMyDetail()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PutMapping("/update-password")
    public User updateMyDetail(@RequestBody SignUpUserDto signUpUserdto)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        String username = user.getUsername();
        User newUser = (User) userService.loadUserByUsername(username);

        // updating the password
        newUser.setPassword(new BCryptPasswordEncoder().encode(signUpUserdto.getPassword()));
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
