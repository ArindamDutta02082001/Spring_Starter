package com.example.demospringsecurity.database.security;

import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*

WebSecurityConfigurerAdapter is a old way to define configs in Spring 2xx and Java 1.8
see the pom.xml , and is obsolete

note :

     if anything is not supporting like the interface WebSecurityConfigurerAdapter or the methods
     then downgrade the application to this project version
     or copy this pom.xml simply
     Spring - 2.7.4
     JDK - 1.8

  we have to
          - @Override 2 configure methods ( one for the authentication manager and the other is for the path mapping and authorities )
            we configure the AuthenticationManager in the first configure function and authorization in the 2nd configure function
            after authentication the authorized personnel can use the endpoints)
            the first configure is optional
          - provide one password encoder bean ( optional , can pass its object into the first configure()

 the AuthenticationManagerBuilder decides what type of authentication is to be provided
 In memory authentication
 In this , there is a inMemoryUserDetailsManager that manages the user details entered into it

 */


@Configuration
@EnableWebSecurity
public class SecurityconfigInDB extends WebSecurityConfigurerAdapter{


    @Autowired
    UserService userService;


    // (optional) not req in spring boot 2xx but we need to manually expose beans
    // in spring boot 3.0 onwards if we use custom filters

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userService);
            //the AuthenticationManagerBuilder decides what type of authentication is to be provided
            //Here we are using user service for DB auth so we aren`t hard coding the user credentials
//    }



    /**
     Authorization related stuff - after the user is authenticated , we are authorizing based on roles
     i.e the user which endpoint he/she has the access
     also the path mapping should be from : most restricted --> least restricted
     */

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

        //                .and()   --> ensures that the functions coming after it are directly attached to the root , HttpClient
        //                .formLogin(); --> this ensure sto give 2 extra api endpoints for form login for authentication
        //                 .anyRequest().authenticated() --> user requesting for any other endpoint (has to be defined in the controller but hsa no authority mentioned)
        //                                                  will have to get authenticated
        //                 .antMatchers("/**").permitAll() --> any user hitting this endpoint will be permitted i.e no auth req



        // most restricted --> least restricted
    }

    // selecting the encoder instance
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }

}
