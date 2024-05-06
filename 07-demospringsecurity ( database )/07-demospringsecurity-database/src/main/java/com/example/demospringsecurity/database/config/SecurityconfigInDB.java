package com.example.demospringsecurity.database.config;

import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
     if anything is not supporting like the interface or the methods
     then downgrade the application to this proect version
     2.7.4
 */


@Configuration
public class SecurityconfigInDB extends WebSecurityConfigurerAdapter{

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

    // selecting the encoder instance
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }

}
