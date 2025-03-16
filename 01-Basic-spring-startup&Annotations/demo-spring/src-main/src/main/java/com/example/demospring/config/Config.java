package com.example.demospring.config;


import com.example.demospring.DILogic;
import com.example.demospring.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 *  1. The classes , that have @Component annotation written over the class , the springboot will create a singleton object of that class upon application start
 *  but the above will happen when we have a default constructor inside that class
 *  as JVM uses default constructor to create bean  new CLassname()
 *
 *  if no default constructor is defined inside that class then , we have to create a config class like this
 *  to tell which constructor to be used by JVM to create the class object by using @Bean
 */

// This class will have no usages  it will be used by default by JVM during bean creation
@Configuration
public class Config {


    Logger logger = LoggerFactory.getLogger(DILogic.class);

    /**
     * when we ue @Bean over a function , that function return an object of a particular class as that class
     * dont have a default constructor and springboot fails to create its bean thus uses
     * the bean function we create below
     */

    // we can create beans of primitive types also like this
    @Bean(name = "primitiveBean")
    public String generateString ()
    {
        logger.info("This is a Bean of String class inside the Beans Class");

        return "String @Bean returned ";          // @Bean method cannot return void
    }

    // person class mei default constructor hoga , thus spring fails to create bean of it and thus bean of person class
    // is created by using this function on application start

    // creating same type of beans by using and resolving ambiguity by @Qualifier & @Primary
    @Bean(name = "firstPersonBean" )
    @Primary
    public Person generatePerson1()       {
        logger.info("This is a first person Bean function inside the Beans Class");
            return new Person("Ankul",34);
    }

    @Bean(name = "secondPersonBean")
    public Person generatePerson2()       {
        logger.info("This is a second person Bean function inside the Beans Class");
            return new Person("Sanal",67);
    }


    // bean creation ke time e we can attach some methods that has to occur before and after
    //bean creation is completed

    @Bean(name = "personBean" , initMethod = "start" , destroyMethod = "destroy")
    public Person generatePersonP()       {
        // custom logic can be applied during bean creation
        boolean flag = false;

        if(flag)
             return new Person("Arindam",34);
        else
            return new Person("Sourav",67);

    }

}
