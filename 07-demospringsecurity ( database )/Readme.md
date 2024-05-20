# This is a SpringBoot Web Security [ In Memory ]
We follow the MVC model
>FLOW  → controller  → service → repository → model

### What is Spring Web Security ?
It is a spring framework that helps in authorization & authentication
- Authentication : check user is a valid user or not
- Authorization : If the user is valid , which endpoint he will have the access
  we have to install `spring-boot-starter-security` . After installation the endpoints will be secured .

### Integrate Spring Security with spring boot App [ In Database ]
- Install the necessary dependencies + spring security dependency `spring-boot-starter-security`
- Create a `SecurityConfig.java file` to define the configurations of the **authentication** & **authorization** policy
- We have to create a **User** entity which will implement the `UserDetails` builtin interface
- We have to create a **UserService** that implements `UserDetailsService` builtin interface
- When you log in with Spring Security, it manages your authentication across multiple requests, despite
  HTTP being stateless . Thus you need to use the authenticated `JSESSIONID` to use the endpoints

> Spring Security is a session based framework that works  in HTTP which instead is a session less protocol
> 1. Session Creation: After successful authentication, an HTTP session is formed. Your authentication
     details are stored in this session.
> 2. Session Cookie: A `JSESSIONID` cookie is sent to your browser, which gets sent back with subsequent
     requests, helping the server recognize your session.
> 3. SecurityContext: Using the `JSESSIONID`, Spring Security fetches your authentication details for each
     request.
> 4. Session Timeout: Sessions have a limited life. If you're inactive past this limit, you're logged out.
> 5. Logout: When logging out, your session ends, and the related cookie is removed.
> 6. Remember-Me: Spring Security can remember you even after the session ends using a different
     persistent cookie (typically have a longer lifespan) .
     In essence, Spring Security leverages sessions and cookies, mainly JSESSIONID, to ensure you rem
     authenticated across requests.


##### Inside the SecurityConfig.java file
- The config file class extends the `WebSecurityConfigurerAdapter` ( spring boot version 2.7.17 )
  **now it has been obsolate , try new**
<pre>
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { }
</pre>
we have to define a `InMemoryUserDetailsManager` to manage the user details
<pre> private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();</pre>
- Now inside the class , we have to `@Override` 2 `configure` methods & provide a password encoder Bean. The **First configure()** is used to provide authentication to the user
 <pre>

// this configure method is defined to authenticate the users

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      
      // few hardcoded users defined
      inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
                .roles("student")
                .build());
        inMemoryUserDetailsManager.createUser(User.builder().username("ram")
                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
                .roles("faculty")
                .build());
        auth.userDetailsService(inMemoryUserDetailsManager);
    }

// new users will be saved by this method
    public void saveEmployee(UserDetails userDetails)
    {
        inMemoryUserDetailsManager.createUser(userDetails);
    }
</pre>

- The **Second configure()** method enables us to provide authorization to the authenticated users
<pre>
// this configure method is defined to authorize the users after they are authenticated 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();

        // most restricted --> least restricted

    }
</pre>

- we provide a **Password encoder** bean
<pre>
// the bean of the type of password encoder to be used is provided
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();

    }
</pre>
>  .and() .formLogin(); -> this ensure sto give 2 extra api endpoints for form login for authentication

#### Disabling CSRF token
- If you dont want to hardcode the user details instead you want to dynamically add user details from the endpoint , instead we want to POST request then
  **we have to disable the csrf token which is a security concern in prod level**
- By default Spring Security doesnt allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests
- Without disabling csrf ,only GET call to the endpoints ( after authentication + authorization ) is allowed
    <pre>
    // csrf token is disabled by csrf().disable(). 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasRole("admin")
                .antMatchers("/faculty/**").hasRole("faculty")
                .antMatchers("/student/**").hasRole("student")
                .antMatchers("/library/**").hasAnyRole("student", "faculty")
                .antMatchers("/**").permitAll()
                .and()
    }
    </pre>
> In the steps the MVC model is not followed but in the project you can see the MVC folder structure

### API endpoints are here
##### general endpoints anyone can use without authentication
- `localhost:9000/home`
- `localhost:9000/shop`

>**Note : for the below endpoints you have to be authenticated i.e to access the endpoint from postman first login through the form and provide the authenticated JSESSIONID in the request header**
##### only student has the access [ if authentication is success ]  | provide the JSESSIONID
- `localhost:9000/student`
- `localhost:9000/library`

##### only faculty has the access [ if authentication is success ] | provide the JSESSIONID
- `localhost:9000/student`
- `localhost:9000/library`

##### only the admin has the access neither the student or faculty [ if authentication is success ] | provide the JSESSIONID
- `localhost:9000//faculty/attendance`
> this will throw error 404 as this endpoint is not present in the controllers

### application.properties file
<pre>
SERVER.port = 9000
spring.datasource.url=jdbc:mysql://localhost:3306/demo_security_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
</pre>