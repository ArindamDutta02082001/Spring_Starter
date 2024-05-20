# This is a SpringBoot Web Security [ In Memory ]
We follow the MVC model
>FLOW  → controller  → service → repository → model

### What is Spring Web Security ?
It is a spring framework that helps in authorization & authentication
- Authentication : check user is a valid user or not
- Authorization : If the user is valid , which endpoint he will have the access
  we have to install `spring-boot-starter-security` . After installation the endpoints will be secured .

### Integrate Spring Security with spring boot App [ In Memory ]
- Install the necessary dependencies + spring security dependency `spring-boot-starter-security`
- Create a `SecurityConfig.java file` to define the configurations of the **authentication** & **authorization** policy
  > we havent created any service , dto for in-memory , we can if needed no issue 
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
@EnableWebSecurity  ( optional annotation )
public class SecurityConfig extends WebSecurityConfigurerAdapter {

// we have to define a `InMemoryUserDetailsManager` to manage the user details
 private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

 }
</pre>

- Now inside the class , we have to `@Override` : `2 configure methods` & provide a `password encoder Bean`. The **First configure()** is used to provide authentication to the user
 <pre>
// this configure method is defined to authenticate the users

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      
      // few hardcoded users defined
        inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
                .authorities("student")
                .build());
        inMemoryUserDetailsManager.createUser(User.builder().username("ram")
                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
                .authorities("faculty")
                .build());
        inMemoryUserDetailsManager.createUser(User.builder().username("ayush")
                .password("$2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6")
                .authorities("admin")
                .build());

    }

// new users will be saved by this method
    public void saveEmployee(UserDetails userDetails)
    {
        inMemoryUserDetailsManager.createUser(userDetails);
    }
</pre>

- The `Second configure()` method enables us to provide authorization to the authenticated users. we can use `hasAnyAuthority` or `hasAuthority` to provide authorization
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

> `.and()` : It means that the next methods that comes after .and() will be joined directly to the root ( HttpSecurity http ).
>
> `.formLogin()` : this ensures to give 2 extra api endpoints (/login , /logout) with a html basic form page for form login for authentication. We can provide our own login page by extending `.formLogin().loginPage("/custom-path")`
>
> `.permitAll()` : it means that any user matching that endpoint can have access
>
> `.csrf().disable()` : By default Spring Security doesnt allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests

- we provide a `Password encoder` bean ( We can pass `new PasswordEncoder()` in the first configure()  )
<pre>
// the bean of the type of password encoder to be used is provided
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();

    }
</pre>


#### Disabling CSRF token
- If you dont want to hardcode the user details instead you want to dynamically add user details from the endpoint , instead we want to POST request then
  **we have to disable the csrf token which is a security concern in prod level**
- By default Spring Security doesnt allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests
- Without disabling csrf ,only GET call is allowed to the endpoints ( after authentication + authorization )
    <pre>
    // csrf token is disabled by csrf().disable(). 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
                .antMatchers("/signup/**").hasAuthority("admin")
                .antMatchers("/credential/**").hasAuthority("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
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

>**Note : for the below endpoints first authenticate i.e to access the endpoint from postman first login through the UI form or login through the `/login?username=something&password=something` and note the authenticated JSESSIONID , and pass that JSESSIONID in the request header**
>
> This has to be done as Spring Security is a session based framework that works  in HTTP which intern is a session less protocol
##### only admin has the access   (hardcoded : ayush as admin , arindam as student , ram as faculty)
- `localhost:9000/login?username=ayush&password=ayush@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- `localhost:9000/credential?username=ram`                 getting credential of a user ( anyone can be )
- `localhost:9000/signup?username=rahim&password=rahim@123&role=student`      creating a new user ( of any role )
  - `localhost:9000/faculty/attendance`       this will throw error 404 as this endpoint is not present in the controllers

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
