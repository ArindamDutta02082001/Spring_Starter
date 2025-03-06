package com.example.demospring.model;


import org.springframework.stereotype.Component;

//@Component
/**
 * here we cannot use @Component as here we dont have default constructor and JVM cant create its object so we need
 * to create a @Bean of it and explicitly give a Person object which it will store in application context


 @Bean
 public Person generatePerson()
 {
 // custom logic can be applied during bean creation
 return new Person("Sourav",67);
 }


 */
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
