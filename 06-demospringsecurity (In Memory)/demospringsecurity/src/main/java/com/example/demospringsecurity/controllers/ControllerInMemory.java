package com.example.demospringsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerInMemory {

    /*
        cannot do any post , put , patch etc except for get as on going to the
        other endpoint it will redirect you to the /login endpoint

     */

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
