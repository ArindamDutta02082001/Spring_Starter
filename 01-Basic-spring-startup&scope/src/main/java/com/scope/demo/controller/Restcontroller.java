package com.scope.demo.controller;


import com.scope.demo.model.Student;
import com.scope.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope(value = "request")
public class Restcontroller {

    @Autowired
    User user;


    @Autowired
    Student student;

    public Restcontroller() {
        System.out.println("initialization started in rest class");
        System.out.println("Student constructor class hashcode2 : "+student.hashCode());
    }


    @GetMapping("/restcontroller")
    public void getUser()
    {
        System.out.println("Hashcode of rest controller : "+this.hashCode());
        System.out.println("User of rest controller : "+user.hashCode());
        System.out.println("Student of rest controller : "+student.hashCode());
        return ;
    }
}
