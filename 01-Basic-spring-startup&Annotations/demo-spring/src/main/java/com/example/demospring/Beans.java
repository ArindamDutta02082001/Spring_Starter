package com.example.demospring;


import com.example.demospring.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 *  1. The classes , that have @Component annotation written over the class ,
 *  the springboot will create a singleton object of that class upon application start
 *  2. the object that is created by the springboot for the @Component annotation class is called as a bean
 */


@Configuration
public class Beans {


    Logger logger = LoggerFactory.getLogger(IOCDILogic.class);

    /**
     * when we ue @Bean over a function , that function return type is created and stored in the IOCDILogic Container
     * when the springboot application starts
     */

    // creating same type of beans and resolving ambiguity by @Qualifier & @Primary

    @Bean(name = "firstBean")
    @Primary
    public String inverseFunction ()
    {
        logger.info("This is a Bean function inside the Beans Class");

        // @Bean method cannot return void
        return "@Bean returned ";
    }

    @Bean(name = "secondBean")
    public String inverseFunction2 ()
    {
        logger.info("This is a Bean function inside the Beans Class");

        // @Bean method cannot return void
        return "@Bean2 returned ";
    }

    @Bean(name = "thirdBean")
    public String inverseFunction3 ()
    {
        logger.info("This is a Bean function inside the Beans Class");

        // @Bean method cannot return void
        return "@Bean3 returned ";
    }

    @Bean(name = "personBean" , initMethod = "start" , destroyMethod = "destroy")
    public Person generatePerson()
    {
        // custom logic can be applied during bean creation
        boolean flag = false;

        if(flag)
             return new Person("Arindam",34);
        else
            return new Person("Sourav",67);

    }



}
