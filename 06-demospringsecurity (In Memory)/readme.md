# This is a SpringBoot rest api demo with redis
We follow the MVC model
>FLOW  → controller  → service → repository → model

### Integrate Spring Security with spring boot App [ In Memory ]
- Install the necessary dependencies + spring security dependency `spring-boot-starter-security`
- Create a **SecurityConfig.java file** to define the configurations in this file
- In this we define the **authentication** & **authorization** policy
    - Authentication : check user is a valid user or not
    - Authorization : If the user is valid , which endpoint he will have the access

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
        inMemoryUserDetailsManager.createUser(User.builder().username("ayush")
                .password("$2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6")
                .roles("admin")
                .build());

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
                .antMatchers("/faculty/attendance/**").hasRole("admin")
                .antMatchers("/faculty/**").hasRole("faculty")
                .antMatchers("/student/**").hasRole("student")
                .antMatchers("/library/**").hasAnyRole("student", "faculty")
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
                .antMatchers("/signup/**").hasRole("admin")
                .antMatchers("/credential/**").hasRole("admin")
                .antMatchers("/faculty/**").hasRole("faculty")
                .antMatchers("/student/**").hasRole("student")
                .antMatchers("/library/**").hasAnyRole("student", "faculty")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();
    }
    </pre>
> In the steps the MVC model is not followed but in the project you can see the MVC folder structure

### API endpoints are here
##### general endpoints anyone can use without authentication
- `localhost:9000/home`
- `localhost:9000/shop`

>**Note : for the below endpoints first authenticate i.e to access the endpoint from postman first login through the UI form or login through the /login?username=something&password=something and note the authenticated JSESSIONID , and pass that JSESSIONID in the request header**
##### only admin has the access   (hardcoded : ayush as admin , arindam as student , ram as faculty)
- `localhost:9000/login?username=ayush&password=ayush@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- `localhost:9000/credential?username=ram`                 getting credential of a user ( anyone can be )
- `localhost:9000/signup?username=rahim&password=rahim@123&role=student`      creating a new user ( of any role )
- `localhost:9000/faculty/attendance`                               
> this will throw error 404 as this endpoint is not present in the controllers

##### only student has the access 
- `localhost:9000/login?username=arindam&password=arindam@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- `localhost:9000/student`
- `localhost:9000/library`

##### only faculty has the access 
- `localhost:9000/login?username=ram&password=ram@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- `localhost:9000/faculty`
- `localhost:9000/library`

### application.properties file
<pre>
SERVER.port = 9000
</pre>