package com.scope.demo.model;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class User {

    @PostConstruct
    public void foo()
    {
        System.out.println("User class hashcode : "+this.hashCode());
    }
}
