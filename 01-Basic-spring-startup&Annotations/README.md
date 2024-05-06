# Spring boot basics

### Default starter code
Default initial code in **DemoSpringAplication.java** we will have a
<pre>
@SpringBootApplication
or
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan
public class DemoSpringAplication
{
public static void main(String[] args)
{
SpringApplication.run(ClassName.class, args);
}
}
</pre>
- **@SpringBootApplication** is an annotation that is the gist of other annotations include
  @SpringBootConfiguration , @EnableAutoConfiguration , @ComponentScan
  which scans the entire application for the beans and other things
- **SpringApplication** provides a convenient way to bootstrap a Spring application and run it by
  the run(). It returns all the beans in the IOC Container

### Necessary dependencies
how to add dependency → go to https://start.spring.io/ , add dependencies
<br/></br>Necessary dependencies to be added
* Dev tools → to autostart the server after code changes | we have to change in settings
* Spring Web → to embed a tomcat server in the project
* H2 database → to embed the default storage in spring boot
* SQL manager/driver , Oracle driver , JDBC → for the sql and jdbc connection
* JUNIT → for execution of the junit test cases
* spring-data-jpa → for using the Hibernate & Jpa in project

### Logger
- logger is just like System.out.print () . but gives the details of when & what class is logged .
  logger can be of different types error , warn , info, debug , trace ( less priority ) .
  <br/></br>in a class file ,
<pre>
public class LoggingController 
{
static Logger logger = LoggerFactory.getLogger(LoggingController.class)

public static void main( String args[] )
    {
        logger.trace("A TRACE Message");
        logger.debug("A DEBUG Message");
        logger.info("An INFO Message");
        logger.warn("A WARN Message");
        logger.error("An ERROR Message");
    }
}
</pre>
- in the **application.properties** , we can choose to show only info , error , debug , warn
  logging.level.root = info / error / debug / warn

### Properties of spring boot
#### Inversion of Control
- passing the authority to make an object of a class to spring during the initial component scan
  phase , and the user dont create an object of the class.
  or , the object lifecycle is managed by the spring boot this is called Inversion of Control
  <br/></br>**@Component**
1. @Component annotation written over the class , the springboot will create a
   singleton object of that class upon application start and stores them in IOC container
2. the object that is created by the springboot for the @Component annotation class is called as a
   bean
3. default constructor is called during bean creation
>IOC Container / Application context → a RAM region where all the beans i.e object of the class are stored
by spring boot
4. we can see all the beans in the IOC container / Application context :

<pre>
public static void main(String[] args)
{
ApplicationContext apc = SpringApplication.run(ClassName.class, args);
for ( String s : apc )
{ 
System.out.println(“Beans are : ” + s ); // all beans }
}</pre>

<br/></br>

#### Dependency Injection
- Any bean ( i.e object ) created by spring boot can be used anywhere anytime required
- DI is implemented by @Autowired and @Bean
  <br/></br>**@Autowired**
1. @Autowired tell that use the singleton object that you created already present in bean and don't create
   a new object , please use that object here
2. @Autowired is not allowed inside function instead we have @Bean ( which creates object of function return type )
3. kitna bar kahi par bhi @Autowired kr lo of the same class , The same bean ( i.e object ) is referred to

<pre>
class IOCClass
{
    @Autowired              // creates the singleton object of Person from bean
    Person person;

    // normal bean          // creates the singleton object of return type of the function
    @Autowired
    @Qualifier(value = "thirdBean")
    String string3;

}

@Component
class BeanClass        // A class for which a bean is created
{

    // normal bean
    @Bean(name = "thirdBean")
    public String inverseFunction3 ()
    {
        return "@Bean3 returned ";
    }

}
</pre>
>in other words we say , Person class is injected into the Shared class or Shared class has a DI on Person

<br/></br>**@Bean**
- we can create a singleton object of a function return type using @Bean code
- function inside @Bean can never return void
- @Configuration is to be used in the class in which @Bean is used
- the @Bean will create a object of the function return type in the IOC container which can be used
  when required
- when multiple function have the same return type then the ambuiguity is ressolved
  by @Primary and @Qualifier

<pre>
class IOCClass
{

    // normal bean              // creates the singleton object of return type of the function
    @Autowired
    @Qualifier(value = "thirdBean")
    String string3;

    // normal bean
    @Autowired
    @Qualifier(value = "thirdBean")
    String string3;

    // function with same return type are segregated by @Primary @Qualifier
    @Autowired            
    @Qualifier(value = "firstBean")
    String string1;

    @Autowired
    @Qualifier(value = "secondBean")
    String string2;

    // bean attached with some functions
    @Autowired
    @Qualifier(value = "personBean")
    Person person;

}

@Configuration
class BeanClass
{
    // normal bean
    @Bean(name = "thirdBean")
    public String inverseFunction3 ()
    {
        return "@Bean3 returned ";
    }

    // function with same return type are segregated by @Primary @Qualifier
    @Bean(name = "firstBean")
    @Primary
    public String inverseFunction ()
    {
        Syso ("This is a Bean function inside the Beans Class");
        return "@Bean1 returned ";
    }

    @Bean(name = "secondBean")
    public String inverseFunction2 ()
    {
        Syso ("This is a Bean function inside the Beans Class");
        return "@Bean2 returned ";
    }

    // init & destroy funtions are attached to the bean
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



public class Person {
    String name ;
    Integer age;

    // runs before the bean is created
    public void start() {
        System.out.println("Init method for the bean started...");
    }

    // runs after the bean is destroyed
    public void destroy() {
        System.out.println("Destroy method for the bean started...");
    }
}

</pre>

### application.properties file
- this file contains the properties like url , port no , DB user name , DB pwd, etc that are needed to run the application
<pre>
spring.datasource.url=jdbc:mysql://localhost:3306/library?createDatabaseIfNotExist=true
spring.datasource.username = root
spring.datasource.password = admin

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update

logging.level.root=debug
server.PORT = 9000
</pre>