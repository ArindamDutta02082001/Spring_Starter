package com.example.demospring;

import com.example.demospring.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class DILogic {

    //  yha pe sysout use kr liya hu bro !
    //    Logger logger = LoggerFactory.getLogger(DILogic.class);


    /** types of Dependency Injection */


    // Dependency Injection by Field Injection
    @Autowired
    @Qualifier(value = "firstPersonBean")
    Person person1;

    @Autowired
//    @Qualifier(value = "secondPersonBean")
    Person person2;



    // Dependency Injection by setter Injection
     String string1;
     @Autowired
     public void setterBeanConstructor( @Qualifier(value = "primitiveBean") String stringBean)
     {
           this.string1 = stringBean;
     }



    // Dependency Injection by constructor Injection
    Person person;
    @Autowired
    public DILogic( @Qualifier(value = "personBean") Person pBean , @Value("${name}") String name )
    {
        this.person = pBean;       //  here we are injecting the bean

        System.out.println("param constructor used for constructor injection ");
    }


    public void CustomFunction()
    {
        System.out.println("********* CustomFunction file *****************");
        System.out.println("This is a Inversion of Control example , this function is called from the main file using @Autowire for DI ");

//        System.out.println("The Person bean is : " + person1.display());
        System.out.println("The Person bean is : " + person2.display());

    }

    // here
//    DILogic()
//    {
//        System.out.println("default constructor of DILogic");
//    }

}
