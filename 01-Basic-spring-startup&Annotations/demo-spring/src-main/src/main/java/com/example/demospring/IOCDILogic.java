package com.example.demospring;

import com.example.demospring.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class IOCDILogic {

    // for storing the application.properties name value
    private String name ;
    Logger logger = LoggerFactory.getLogger(IOCDILogic.class);


    // Dependency Injection by Field Injection
    @Autowired
    @Qualifier(value = "firstBean")
    String string1;

    @Autowired
    @Qualifier(value = "personBean")
    Person person;



    // Dependency Injection by setter Injection
        String string2;
        @Autowired
        public void secondBean( @Qualifier(value = "secondBean") String secondBean)
        {
            this.string2 = secondBean;
        }

    // Dependency Injection by constructor Injection
    String string3;
    @Autowired
    public IOCDILogic( @Qualifier(value = "thirdBean") String thirdBean , @Value("${name}") String name)
    {
        this.string3 = thirdBean;

        this.name=name;
        logger.info("param constructor "+name);
    }



    public void IOCFunction()
    {
        System.out.println("********* IOCDILogic file *****************");
        logger.info("This is a Inversion of Control example");
        System.out.println("The three beans are : "+ string1 + string2 + string3);
        System.out.println("The Person bean is : " + person.display());

    }

    IOCDILogic()
    {
        logger.info("default contructor");
    }



}
