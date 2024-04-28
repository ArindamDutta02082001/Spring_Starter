package com.example.demospring;

import com.example.demospring.entity.Person;
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


    // Dependency Injection
    @Autowired
    @Qualifier(value = "firstBean")
    String string1;

    @Autowired
    @Qualifier(value = "secondBean")
    String string2;

    @Autowired
    @Qualifier(value = "thirdBean")
    String string3;

    @Autowired
    @Qualifier(value = "personBean")
    Person person;


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

    IOCDILogic(@Value("${name}") String name )
    {
        this.name=name;
        logger.info("param contructor "+name);
    }

}
