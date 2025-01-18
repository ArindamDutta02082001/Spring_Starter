package com.example.demospring.entity;

public class Person {
    String name ;
    Integer age;

   public Person(String name, Integer age)
   {
       this.name = name;
       this.age = age;

   }

   public String display()
   {
       return ("Name : "+name+" Age : "+age);
   }

    // runs before the bean is created
    public void start() {
        System.out.println("Init method for the bean started...");
    }

    // runs after the bean is destroyed
    public void destroy() {
        System.out.println("Destroy method for the bean started...");
    }
}
