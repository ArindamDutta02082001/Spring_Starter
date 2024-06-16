package com.demo.oauth2.controllers;

import com.demo.oauth2.filterAndTokenManager.RefreshTokenManager;
import com.demo.oauth2.dto.authenticateDto;
import com.demo.oauth2.dto.registerDto;
import com.demo.oauth2.dto.responseDto;
import com.demo.oauth2.filterAndTokenManager.JWTTokenManager;
import com.demo.oauth2.models.DemoUser;
import com.demo.oauth2.repository.UserRepository;
import com.demo.oauth2.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class JWTController {

    @Autowired
    UserRepository userRepository;


    @Autowired
    JWTTokenManager JWTTokenManager;

    @Autowired
    RefreshTokenManager refreshTokenManager;


    @Autowired
    UserService userService;


    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/refresh-token")
    public responseDto refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
      return (responseDto) refreshTokenManager.getAccessTokenFromrefreshToken(request, response);
    }


    @PostMapping("/authenticate")
    public responseDto generateAccessToken(@RequestBody authenticateDto request) throws Exception
    {

        // authentication part with id and pwd
        try
        {
        this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword()));

        DemoUser demoUser = (DemoUser) this.userService.loadUserByUsername(request.getUsername());

        // generating WT and refresh token for the first time user
            String JWTAccessToken = this.JWTTokenManager.generateToken(demoUser);
            String RefreshToken = this.refreshTokenManager.generateRefreshToken(demoUser);
            // save the refresh token
            this.refreshTokenManager.saveRefreshTokeninDB(RefreshToken , demoUser);

            return new responseDto(demoUser.getUsername(),JWTAccessToken ,RefreshToken);
        }
        catch (UsernameNotFoundException e)     // if not found then exception
        {
            System.out.println(e.getStackTrace());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        return new responseDto("invalid username","Invalid ID or PWD" , null);

    }


    @PostMapping("/register")
    public responseDto registerUser(@RequestBody registerDto request) throws Exception {

        DemoUser demoUser = userService.createUser(request);

        // generating WT and refresh token for the first time user
        String JWTAccessToken = this.JWTTokenManager.generateToken(demoUser);
        String RefreshToken = this.refreshTokenManager.generateRefreshToken(demoUser);
        // save the refresh token
        this.refreshTokenManager.saveRefreshTokeninDB(RefreshToken, demoUser);

//        userRepository.save(demoUser);

        return new responseDto(demoUser.getUsername(), JWTAccessToken, RefreshToken);
    }
}
