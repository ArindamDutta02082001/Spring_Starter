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
- config : Create a `SecurityConfig.java file` to define the configurations for the **authentication** & **authorization** policy
- dto : For accepting the username , password , authorities in the endpoints
- model : Create a **User** entity which will implement the `UserDetails` builtin interface
```
@Lombok-Annotations
public class User implements UserDetails 
{ 
// implement interface methods isEnabled , isCredentialsNonExpired , isAccountNonLocked , isAccountNonExpired , getUsername , getPassword 
// - do -
}
```
- service : Since InMemory , so our service file don't implements `UserDetailsService`
- repository : We have to create a **RepositoryClass.java** file to manage the `User` Entities . There exists a `InMemoryUserDetailManager` to manage them
```dockerfile
    public InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
    
    Contructor()
    {
        inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
                .authorities("student")
                .build());
            -- pre defined users are hardcoded here -- 
    }

    public InMemoryUserDetailsManager createInMemoryUserDetailManager()
    {
        return this.inMemoryUserDetailsManager;
    }
```
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
  **now it has been out-dated , now we use SecurityFilterChain**
```dockerfile
@Configuration
@EnableWebSecurity  ( optional annotation )
public class SecurityConfig extends WebSecurityConfigurerAdapter { }
```

- Now inside the class , we have to `@Override` : `2 configure methods` & provide a `password encoder Bean`
```dockerfile
// FIRST CONFIGURE METHOD IS DEFINED TO AUTHENTICATE THE USERS

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inmemoryService.getInMemoryUserDetailManager());
    }
```

```dockerfile
// SECOND CONFIGURE METHOD ENABLES US TO PROVIDE AUTHORIZATION TO THE AUTHENTICATED USERS BASED ON ROLE AND AUTHORITY.
// WE USE `HASANYAUTHORITY` OR `HASAUTHORITY` TO PROVIDE AUTHORIZATION
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
                .antMatchers("/credential/**").hasAnyAuthority("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .antMatchers("/home").permitAll()
                .antMatchers("/shop").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();

        // most restricted --> least restricted
    }
```

> `.and()` : It means that the next methods that comes after .and() will be joined directly to the root ( HttpSecurity http ).
>
> `.formLogin()` : this ensures to give 2 extra api endpoints (/login , /logout) with a html basic form page for form login for authentication. We can provide our own login page by extending `.formLogin().loginPage("/custom-path")` .
instead of `.formLogin()` we can use `httpBasic() , No Auth , Bearer , OAuth2.0` mode of sending credentials 
>
> `.permitAll()` : it means that any type of user matching that endpoint can have access
>
> `.csrf().disable()` : By default Spring Security doesnt allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests
>
> `.anyRequest().authenticated()` : user requesting for any other endpoint not matching to above rules (but defined in the controller) will have to get authenticated

- we provide a `Password Encoder` bean
```dockerfile
// the bean of a password encoder bean to be used is provided
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();

    }
```


#### Disabling CSRF token
- If you dont want to hardcode the user details instead you want to dynamically add user details from the endpoint , instead we want to POST request then
  **we have to disable the csrf token which is a security concern in prod level**
- By default, Spring Security doesn't allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests
- Without disabling csrf ,only GET call is allowed to the endpoints ( after authentication + authorization )
    ```dockerfile
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
   ```

> In the steps the MVC model is not followed but in the project you can see the MVC folder structure

### API endpoints are here
##### general endpoints anyone can use without authentication
- GET : `localhost:9000/home`
- GET : `localhost:9000/shop`

>**Note : for the below endpoints first authenticate i.e to access the endpoint from postman first login through the UI form or login through the `/login?username=something&password=something` and note the authenticated JSESSIONID , and pass that JSESSIONID in the request header**
>
> This has to be done as Spring Security is a session based framework that works  in HTTP which intern is a session less protocol
##### only admin has the access   (hardcoded : ayush as admin , arindam as student , ram as faculty)
- POST : `localhost:9000/login?username=ayush&password=ayush@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/credential?username=ram`                 getting credential of a user ( anyone can be )
- POST : `localhost:9000/signup?username=rahim&password=rahim@123&role=student`      creating a new user ( of any role )
- GET : `localhost:9000/faculty/attendance`       this will throw error 404 as this endpoint is not present in the controllers

##### only student has the access
- POST : `localhost:9000/login?username=arindam&password=arindam@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/student`
- GET : `localhost:9000/library`

##### only faculty has the access
- POST : `localhost:9000/login?username=ram&password=ram@123`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/faculty`
- GET : `localhost:9000/library`

##### SPRING SECURITY CONTEXT : only the logged-in personnel can access this
- GET : `localhost:9000/my-detail`  no need to pass user_id to fetch the details

### Spring Security Context ?
it contains the object details just like an Application Context holding details of the Beans .
It gives the object details based on the authentication details ( username & password ) that we pass in the beginning

### application.properties file
<pre>
SERVER.port = 9000
</pre>
