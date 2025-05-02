# Springboot basics + features (IOC , DI) + pom.xml + @Annotations + Multi-modules

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

- we can create a singleton object of a class and return in the function as the return type 
- A function return type is generally a class object that we give to JVM to store as a bean in the container . function inside @Bean can never return void 
- @Configuration is to be used in the class in which @Bean is used
- the @Bean will tell the JVM to create a object/bean of the class , for which we are returning the object into the IOC container ( or Application Context ) which can be used when required using @Autowired
- if there is the same return type of Beans and there is some ambiguity then we use @Primary or @Qualifier to solve it


```dockerfile
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
        logger.info("This is a Beanof String class inside the Beans Class");

        // @Bean method cannot return void
        return "String @Bean returned ";
    }

    // person class mei default constructor hoga , thus spring fails to create bean of it and thus bean of person class
    // is created by using this function on application start

    // creating same type of beans by using and resolving ambiguity by @Qualifier or @Primary
    @Bean(name = "firstPersonBean" )
    @Primary         // --> if we dont use qualifier explicitly then this bean will be used
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
    // bean creation is completed

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

// here if you try to use @Component here it will throw error as no default constructor can be created
// so for this Person class we need to use @Bean to create a bean of it
public class Person {
    String name ;
    Integer age;

  public Person(String name, Integer age)
   {
       this.name = name;
       this.age = age;

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

```

#### Dependency Injection
- It happens automatically in step3 in the bean lifecycle where ever we use @Autowired 
- Any bean ( i.e object ) present in IOC Container can be used anywhere anytime required using @Autowired 
- It is a Design Pattern that helps to eliminate the dependency to a class , and provides loose coupling and also eliminate to use new() every tie to create a new object 
- It helps us to use the already created beans in the IOC container 
- DI is of 3 types Constructor , Setter and Field injection and is implemented by @Autowire 
- DI help us to follow Dependency Inversion in SOLID principle
  Dependency Inversion → My class should not dependent on any class directly (avoid tight coupling) rather depend on the abstractions ( like interface and all )

  <br/></br>**@Autowired**

1. It  tell that use the singleton object that you created already present in bean and don't create a new object , please use that object here
2. It helps in Dependency Injection . Also kitna bar kahi par bhi @Autowire kr lo of the same class, The same bean ( i.e object ) is referred to
3. @Autowire variables can’t be declared inside function ( class loading ke time pe woh load hota hai ) so always use inside the class directly
4. @Autowire fields cannot be final in case of field & setter injection but you can use in case of constructor or setter injection
5. make sure the class that you are Autowiring is already there in the IOC container i.e that class bean is created by using @Conainer or @Bean

>in other words we say , Person class is injected into the Shared class or Shared class has a DI on Person

```dockerfile
@Component
public class DILogic {

    /** types of Dependency Injection */


    // Dependency Injection by Field Injection
    @Autowired
    @Qualifier(value = "firstPersonBean")
    Person person1;

    @Autowired
    @Qualifier(value = "secondPersonBean")
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
    public DILogic( @Qualifier(value = "personBean") Person pBean , @Value("${name}") String name)
    {
        this.person = pBean;       //  here we are injecting the bean

        System.out.println("param constructor used for constructor injection ");
    }
}
```

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

### Multi-module project and pom.xml explanations
- this has 2 modules `src-main` & `src-mod-2`.  
- `demo-spring` is the main project that contains multiple modules .`src-main` is the main module and `src-mod-2` is a module that contains utility
- The SampleDto of `src-mod-2` is used in the other module i.e `src-main`
- See how the `SampleDto` is injected in the `MainClass` of `src-main` module
- See how the config changes are there in pom.xml of both  

```parent-project/
│
├── pom.xml               <-- Master POM
├── src-main/
│   └── pom.xml           <-- Submodule 1
└── src-mod-2/
└── pom.xml           <-- Submodule 2
```


1. *There is a master pom.xml in the `demo-spring` folder only :*
  
It has a identity
```
<groupId>com.example.demospring</groupId>
<artifactId>demo-spring</artifactId>
<version>0.0.1-SNAPSHOT</version>
```

2. *in the modules pom we need to include the other module`s pom if we are depending on them :*  

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

3. we include the submodules inside the parent pom . so that the same dependencies are not rewritten inside the child poms
   also to synchronize the MVN commands  i.e when this is build the sub-modules are also built. If you dont do this build and other stuffs will happen part by part else err
```dockerfile
	<modules>
		<module>src-main</module>
		<module>src-mod-2</module>
	</modules>
```
we have to make the parent pom `demo-spring` packaging as pom , so that other modules can include in their poms
  ```<packaging>pom</packaging> ```

4. we have to refer to the master pom inside sub-modules as we are inheriting the dependencies of the master pom and not rewriting them
*in child pom.xml (here src-mod) :*
```
    <parent>
        <groupId>com.example.demospring</groupId>
        <artifactId>demo-spring</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```
*in child pom.xml (here src-mod-2) :*
```
    <parent>
        <groupId>com.example.demospring</groupId>
        <artifactId>demo-spring</artifactId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
```