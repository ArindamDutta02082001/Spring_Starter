package com.demo.oauth2.controllers;

import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class OAuthController {


    @GetMapping("/")
    public String getUserDetails() {

        return "Hi Welcome you are Authenticated";

    }


    @GetMapping("/user")
    public Principal showUser(Principal p) {
        System.out.println(p.getClass().getName());
        return p;
    }
}
