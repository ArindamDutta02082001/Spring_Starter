package com.example.demospringsecurity.config;

import com.example.demospringsecurity.repository.RepositoryClass;
import com.example.demospringsecurity.service.InmemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
public class SecurityconfigInMemory extends WebSecurityConfigurerAdapter {


    // we have to
    //          - @Override 2 configure methods
    //          - provide one password encoder function ( optional , can pass its object into the first configure() )

    // the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // In memory authentication
    // In this , there is a inMemoryUserDetailsManager that manages the user details entered into it


    // we provide the authentication in the first configure function and authorization in the 2nd configure function
    // after authentication the authorized personnel can use the endpoints

    @Autowired
    InmemoryService inmemoryService;

//    -------------------------------------------------------------------------------------------------------------
    // method 1 and 2 are having there InMemory database in this file only and the endpoint are using directly from this file only

    // method 1

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//        // defines the type of authentication we want to have in app
//        auth.inMemoryAuthentication()
//                .withUser("arindam")
//                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
//                .authorities("student")
//                .and()
//                .withUser("ram")
//                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
//                .authorities("faculty");
//
//    }

    // method 2
//    private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
//                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
//                .authorities("student")
//                .build());
//        inMemoryUserDetailsManager.createUser(User.builder().username("ram")
//                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
//                .authorities("faculty")
//                .build());
//        auth.userDetailsService(inMemoryUserDetailsManager);
//    }
//
//    public void saveEmployee(UserDetails userDetails)
//    {
//
//        inMemoryUserDetailsManager.createUser(userDetails);
//    }

//------------------------------------------------------------------------------------------------------------
    // in method 3 the  InMemory database is there in the repository file and the endpoints are using it through this config file

    // method 3
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(inmemoryService.getInMemoryUserDetailManager());
    }


    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAuthority("admin")
                .antMatchers("/signup/**").hasAuthority("admin")
                .antMatchers("/credential/**").hasAuthority("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .antMatchers("/home").permitAll()
                .antMatchers("/shop").permitAll()
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






    // spring security Encoder instance - The passwords that are passed from UI are to be encoded and matched,
    // you need to make sure that the data present in Storage should be in this format

    /*
    A. a normal noOpsEncoder instance - passwords are compared in raw string format
    */

//    @Bean
//    PasswordEncoder encoderInstance()
//    {
//        return NoOpPasswordEncoder.getInstance();
//    }

    /*
     B. BcryptEncoder - the passwords are hashed upto 10 rounds

     useful site - https://bcrypt-generator.com/

     arindam@123 - $2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W
     ram@123 - $2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq
     ayush@123 - $2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6


    */
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();              // by default the number of rounds are 10

    }





}
