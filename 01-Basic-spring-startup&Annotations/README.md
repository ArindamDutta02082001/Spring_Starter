# Springboot basics + features (IOC , DI) + pom.xml + @Annotations

### Default starter code
Default initial code inside src/main/java/com/example **DemoSpringApplication.java** we will have a
```
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
```
- **@SpringBootApplication** is an annotation that is the gist of other annotations include
  @SpringBootConfiguration , @EnableAutoConfiguration , @ComponentScan
  which scans the entire application for the beans and other things
- **SpringApplication** provides a convenient way to bootstrap a Spring application and run it by
  the run(). It returns all the beans in the IOC Container
<hr/>

### Necessary dependencies
how to add dependency → go to https://start.spring.io/ , add dependencies
<br/></br>Necessary dependencies to be added
* Dev tools → to autostart the server after code changes | we have to change in settings
* Spring Web → to embed a tomcat server in the project
* H2 database → to embed the default storage in spring boot
* SQL manager/driver , Oracle driver , JDBC → for the sql and jdbc connection
* JUNIT → for execution of the junit test cases
* spring-data-jpa → for using the Hibernate & Jpa in project
<hr/>

### Logger
- logger is just like System.out.println() . but gives the details of when & what class is logged .
  logger can be of different types error , warn , info, debug , trace ( less priority ) .
   
in a class file ,
```dockerfile

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
```
- in the **application.properties** , we can choose to show only info , error , debug , warn
  `logging.level.root = info / error / debug / warn`
<hr/>

### Properties of spring boot
#### Inversion of Control
- passing the authority to make an object of a class to spring during the initial component scan
  phase , and the user don't create an object of the class.
  or , the object lifecycle is managed by the spring boot this is called Inversion of Control
- The object created by Sring boot is called a Bean
  All Beans are kept in IOC Container / Application context ( a RAM region where all the beans i.e object of the class are stored by spring boot )
  These Beans or the objects can be created by Spring boot by 2 annotations @Component & @Bean

  <br/></br>**@Component**
1. @Component annotation written over the class , the springboot will create a
   singleton object of that class upon application start and stores them in IOC container
2. the object that is created by the springboot for the @Component annotation class is called as a
   bean
3. default constructor is called during bean creation
4. we can see all the beans in the IOC container / Application context :

```dockerfile

public static void main(String[] args)
{
ApplicationContext apc = SpringApplication.run(ClassName.class, args);
for ( String s : apc )
{ 
System.out.println(“Beans are : ” + s ); // all beans }
}
```

<br/></br>**@Bean**
- we can create a singleton object of a function return type using @Bean code
- function inside @Bean can never return void
- @Configuration is to be used in the class in which @Bean is used
- the @Bean will create a object of the function return type in the IOC container which can be used
  when required
- when multiple function have the same return type then the ambiguity is resolved
  by @Primary and @Qualifier

```dockerfile
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

```

#### Dependency Injection
- Any bean ( i.e object ) created by spring boot can be used anywhere anytime required
- It is a Design Pattern that helps to eliminate the dependency to a class , and provides loose coupling
- DI is of 3 types Constructor , Setter and Field injection and is implemented by @Autowire [medium_link](https://medium.com/@reetesh043/spring-boot-dependency-injection-137f85f84590)


  In our projects we have used Field Injection , which is implemented by

  <br/></br>**@Autowired**

1. @Autowired tell that use the singleton object that you created already present in bean and don't create
   a new object , please use that object here
2. It helps in Dependency Injection
3. @Autowire variables can’t be declared inside function and cannot be static so always use inside the class directly 
4. make sure the class that you are Autowiring is already there in the IOC container i.e that class bean is created by using @Conainer or @Bean
5. kitna bar kahi par bhi @Autowire kr lo of the same class, The same bean ( i.e object ) is referred to

```dockerfile
@Component
public class IOCDILogic 
{

// Dependency Injection by Field Injection
    @Autowired
    @Qualifier(value = "firstBean")
    String string1;

    @Autowired
    @Qualifier(value = "personBean")
    Person person;		
** in other words we say , Person class is injected into this class & this class has a DI on Person


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
    }
 }
```
>in other words we say , Person class is injected into the Shared class or Shared class has a DI on Person

<hr/>

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

<hr/>

### Multi-module project 
- this has 2 modules `src-main` & `src-mod-2`.  `src-main` is the main one and `src-mod-2` is a module that contains utility
- The SampleDto of `src-mod-2` is used in the other module i.e `src-main`
- See how the `SampleDto` is injected in the `MainClass` of `src-main` module
- See how the config changes are there in pom.xml of both  

*There is a master pom.xml in the demo-spring folder only :*
  
It has a identity
```
<groupId>com.example.demospring</groupId>
<artifactId>demo-spring</artifactId>
<version>0.0.1-SNAPSHOT</version>
```
*in parent pom.xml (here src-main) :*  
1. we have to give the dependency of the child module (src-mod-2) in the parent pom.xml
``` 
      <dependencies>
        <dependency>
            <groupId>version-module-src-2</groupId>
            <artifactId>src-mod-2</artifactId>
            <version>0.0.1-SNAPSHOT</version>
            <scope>compile</scope>
        </dependency>
      </dependencies>
```

2. we have to make the parent packaging as pom
  ```<packaging>pom</packaging> ```

3. we have to refer to the master pom as we are inheriting the dependencies of the master pom here
```
    <parent>
        <groupId>com.example.demospring</groupId>
        <artifactId>demo-spring</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```
*in child pom.xml (here src-mod-2) :*  
1. Since here also we are inheriting the dependencies of the master pom here , instead of mentioning each dependencies
```
    <parent>
        <groupId>com.example.demospring</groupId>
        <artifactId>demo-spring</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```