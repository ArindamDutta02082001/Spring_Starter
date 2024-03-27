package com.example.demospring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


/**
 *  1. The classes , that have @Component annotation written over the class ,
 *  the springboot will create a singleton object of that class upon application start
 *  2. the object that is created by the springboot for the @Component annotation class is called as a bean
 */

@Component
class IOC
{

    private String name ;

    Logger logger = LoggerFactory.getLogger(IOC.class);
    public void IOCFunction()
    {
        logger.info("This is a Inversion of Control example");
    }

    IOC()
    {
        logger.info("default contructor");
    }

    IOC( @Value("${name}") String name )
    {
        this.name=name;
        logger.info("param contructor "+name);
    }

}

@Component
public class InversionOfControl {


    Logger logger = LoggerFactory.getLogger(IOC.class);

    @Autowired
    IOC ic;


    /**
     * when we ue @Bean over a function , that function is created and stored in the IOC Container
     * when the springboot application starts
     */

    @Bean
    public void inverseFuncton ()
    {
        logger.info("This is a Bean function inside the InversionOfControl Class");
    }

}
