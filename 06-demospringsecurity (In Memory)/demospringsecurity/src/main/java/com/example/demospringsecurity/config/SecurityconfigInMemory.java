package com.example.demospringsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
for some reason WebSecurityConfigurerAdapter was not coming here ,
if same issue persists then copy this pom.xml file
 */
@Configuration
public class SecurityconfigInMemory extends WebSecurityConfigurerAdapter {

    // In memory authentication
    // In this , the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // Here we are using InMemory auth so only these users with this passwords will be allowed to use the
    // apis defined in the controllers
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.inMemoryAuthentication()
                .withUser("arindam")
                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
                .authorities("student")
                .and()
                .withUser("ram")
                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
                .authorities("faculty");
    }






    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .antMatchers("/faculty/attendance/**").hasAnyAuthority("admin")
                .antMatchers("/faculty/**").hasAuthority("faculty")
                .antMatchers("/student/**").hasAuthority("student")
                .antMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();

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

     useful site - https://bcrypt-generator.com/
    */
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }





}
