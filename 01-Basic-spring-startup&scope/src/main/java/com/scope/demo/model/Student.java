package com.scope.demo.model;


import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request")

public class Student {


    Student()
    {
        System.out.println("Student constructor class hashcode : "+this.hashCode());
    }

    @PostConstruct
    public void foo()
    {
        System.out.println("Student class hashcode : "+this.hashCode());
    }
}
