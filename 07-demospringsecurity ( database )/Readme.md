# This is a SpringBoot Web Security [ In Database ]
We follow the MVC model
>FLOW  → controller  → service → repository → model

### What is Spring Web Security ?
It is a spring framework that helps in authorization & authentication
- Authentication : check user is a valid user or not
- Authorization : If the user is valid , which endpoint he will have the access
  we have to install `spring-boot-starter-security` . After installation the endpoints will be secured .

### Integrate Spring Security with spring boot App [ In Database ]
- Install the necessary dependencies + spring security dependency `spring-boot-starter-security`
- config : Create a `SecurityConfig.java file` to define the configurations for the **authentication** & **authorization** policy
- dto : For accepting the username , password , authorities in the endpoints 
- model : We have to create a **User** entity which will implement the `UserDetails` builtin interface
```dockerfile
@Lombok-annotations
public class User implements UserDetails 
{ 
// implement default methods isEnabled , isCredentialsNonExpired , isAccountNonLocked , isAccountNonExpired , getUsername , getPassword 
// - do -
}
```
- service : We have to create a **UserService** class that implements `UserDetailsService` builtin interface. We have to override the `loadUserByUsername()` so that it returns a User ( if present in DB ) when the `/login` endpoint is HIT
```dockerfile
@Service
public class UserService implements UserDetailsService {
    
    // implement default method loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    // other save , etc functions write as requirement
```
- repository : We have to create a **UserRepository** class to store the `User` Entity
```dockerfile
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from User u where u.username = :username")
    public User findByUsername(String username) ;
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
  **now it has been outdated , we use SpringFilterChain**
```dockerfile
`
@Configuration
@EnableWebSecurity  ( optional annotation )
public class SecurityConfig extends WebSecurityConfigurerAdapter { 

// we define a user service
   @Autowired
   UserService userService;

}
```

- Now inside the class , we have to `@Override` 2 `configure` methods & provide a `password encoder Bean`
```dockerfile
// FIRST CONFIGURE METHOD IS DEFINED TO AUTHENTICATE THE USERS

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.userDetailsService(userService);
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
                .antMatchers("/usersignup").permitAll()
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

- we provide a `Password encoder` bean
```dockerfile
// the bean of the type of password encoder to be used is provided
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();

    }
```

#### Disabling CSRF token
- If you dont want to hardcode the user details instead you want to dynamically add user details from the endpoint , instead we want to POST request then
  **we have to disable the csrf token which is a security concern in prod level**
- By default Spring Security doesnt allow to do UNSAFE methods like PUT POST DELETE PATCH etc , with csrf enabled . So we have to disable it before doing such requests
- Without disabling csrf ,only GET call to the endpoints ( after authentication + authorization ) is allowed
    ```dockerfile
    // csrf token is disabled by csrf().disable(). 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
                //  . . . . 
                //  . . . . 
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
- POST : `localhost:9000/usersignup`        for creating new user
    <pre>
    payload : 
    {
     "username": "ram" , 
     "password": "ram@123",
     "authorities": "faculty"
    }
    </pre>
>**Note : for the below endpoints first authenticate i.e to access the endpoint from postman first login through the UI form or login through the `/login?username=something&password=something` and note the authenticated JSESSIONID , and pass that JSESSIONID in the request header**
>
> This has to be done as Spring Security is a session based framework that works  in HTTP which intern is a session less protocol
##### only admin has the access
- POST : `localhost:9000/login?username=<create_it>&password=<create_it>`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/credential?username=ram`                 getting credential of a user ( anyone can be )
- GET : `localhost:9000/credential/all`      get all user credentials

##### only student has the access
- POST : `localhost:9000/login?username=<create_it>&password=<create_it>`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/student`
- GET : `localhost:9000/library`

##### only faculty has the access
- POST : `localhost:9000/login?username=<create_it>&password=<create_it>`    [  authentication is success | capture the JSESSIONID from cookie ]
- GET : `localhost:9000/faculty`
- GET : `localhost:9000/library`

##### SPRING SECURITY CONTEXT : only the logged-in personnel can access this
- GET : `localhost:9000/my-detail`  no need to pass user_id to fetch the details
- PUT : `localhost:9000/update-password`  to update password of only logged-in account
  <pre>
  {
    "password": "arindam@1234"
  }
  </pre>
- DELETE : `localhost:9000/delete-detail`  to delete the currently logged-in account

### Spring Security Context ?
it contains the object details just like an Application Context holding details of the Beans .
It gives the object details based on the authentication details ( username & password ) that we pass in the beginning

### application.properties file
<pre>
SERVER.port = 9000
spring.datasource.url=jdbc:mysql://localhost:3306/demo_security_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
</pre>