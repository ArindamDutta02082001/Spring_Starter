package com.example.demospringsecurity.config;

import com.example.demospringsecurity.repository.RepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
 */
@Configuration
public class SecurityconfigInMemory extends WebSecurityConfigurerAdapter {

    // In memory authentication
    // In this , there is a inMemoryUserDetailsManager that manages the user details entered into it
    // we have to
    //          - @Override 2 configure methods
    //          - provide one password encoder function
    // the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // we provide the authentication in the first configure function and authorization in the 2nd configure function
    // after authentication the authorized personnel can use the endpoints

    @Autowired
    RepositoryClass repositoryClass;

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
//                .roles("student")
//                .and()
//                .withUser("ram")
//                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
//                .roles("faculty");
//
//    }

    // method 2
//    private final InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
//                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
//                .roles("student")
//                .build());
//        inMemoryUserDetailsManager.createUser(User.builder().username("ram")
//                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
//                .roles("faculty")
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

        auth.userDetailsService(repositoryClass.getInMemoryUserDetailsManager());
    }

    // to save a particular employee in the DB
    public void saveUserDetailsInDB(UserDetails userDetails)
    {
        repositoryClass.saveUserDetails(userDetails);

    }

    // to get a particular employee information from the IN memory DB
    public UserDetails getUserDetailInDB(String username)
    {
        return repositoryClass.getUserDetail(username);
    }

    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access
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

        //                 .and()
        //                .formLogin(); --> this ensure sto give 2 extra api endpoints for form login for authentication

        // most restricted --> least restricted
    }






    // spring security Encoder instance - The passwords that are passed from UI are to be encoded and matched,
    // so we have to provide a type of encoder instance that we want to use

    /*
    A. a normal noOpsEncoder instance - pwds are compared in raw format ,
    bohot basic sa encoder hai
    */

//    @Bean
//    PasswordEncoder encoderInstance()
//    {
//        return NoOpPasswordEncoder.getInstance();
//    }

    /*
     B. BcryptEncoder - the passwords are bcrypted
     you need to make sure that the data present in memory should be in this format

     arindam@123 - $2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W
     ram@123 - $2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq
     ayush@123 - $2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6

     useful site - https://bcrypt-generator.com/
    */
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }





}
