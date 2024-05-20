package com.example.demospringsecurity.database.config;

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
     for some reason WebSecurityConfigurerAdapter was not coming here ,
     if same issue persists then copy this pom.xml file

     if anything is not supporting like the interface or the methods
     then downgrade the application to this project version
     2.7.4

     @EnableWebSecurity  - optional

 */


@Configuration
@EnableWebSecurity
public class SecurityconfigInDB extends WebSecurityConfigurerAdapter{

    // we have to
    //          - @Override 2 configure methods
    //          - provide one password encoder function ( optional , can pass its object into the first configure() )

    // the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // In database authentication
    // In this , there is a repository class that manages to store the user details entered into it


    // we provide the authentication in the first configure function and authorization in the 2nd configure function
    // after authentication the authorized personnel can use the endpoints

    @Autowired
    UserService userService;

    // Database authentication
    // In this , the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // Here we are using user service for DB auth so we aren`t hard coding the user credentials

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.userDetailsService(userService);
    }



    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
//                .antMatchers("/credential/**").hasRole("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();

        //                .and()   --> ensures that the functions coming after it are directly attached to the root , HttpClient
        //                .formLogin(); --> this ensure sto give 2 extra api endpoints for form login for authentication

        // most restricted --> least restricted
    }

    // selecting the encoder instance
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }

}
