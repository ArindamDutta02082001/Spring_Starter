package com.scope.demo.model;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class User {

    @PostConstruct
    public void foo()
    {
        System.out.println("User class hashcode : "+this.hashCode());
    }
}
