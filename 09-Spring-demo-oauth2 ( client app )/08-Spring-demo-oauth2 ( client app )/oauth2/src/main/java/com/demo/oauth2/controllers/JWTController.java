package com.demo.oauth2.controllers;

import com.demo.oauth2.dto.requestDto;
import com.demo.oauth2.dto.responseDto;
import com.demo.oauth2.JWTTokenManager.JWTTokenManager;
import com.demo.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JWTController {

    @Autowired
    JWTTokenManager JWTTokenManager;

    @Autowired
    UserService userService;


    @Autowired
    AuthenticationManager authenticationManager;


    // endpoint that client hits the first time to get the access token
    @PostMapping("/token")
    public responseDto generateAccessToken(@RequestBody requestDto request) throws Exception
    {

        // authentication part with id and pwd
        try
        {

        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        UserDetails userDetails = this.userService.loadUserByUsername(request.getUsername());

            String token = this.JWTTokenManager.generateToken(userDetails);


            responseDto response = new responseDto(userDetails.getUsername(),token);

            return response;
        }
        catch (UsernameNotFoundException e)     // if not found then exception
        {
            System.out.println(e.getStackTrace());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return new responseDto("invalid username","Invalid ID or PWD");

    }


}
