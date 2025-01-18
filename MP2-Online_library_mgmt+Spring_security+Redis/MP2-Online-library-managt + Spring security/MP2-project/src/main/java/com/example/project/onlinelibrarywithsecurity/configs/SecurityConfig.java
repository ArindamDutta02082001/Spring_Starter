package com.example.project.onlinelibrarywithsecurity.configs;

import com.example.project.onlinelibrarywithsecurity.service.securityService.SecuredUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecuredUserService securedUserService;

    // Database authentication
    // In this , the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // Here we are using user service for DB auth so we aren`t hard coding the user credentials

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.userDetailsService(securedUserService);
    }

    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // GET HEAD are safe methods
        // other methods like PUT POST UPDATE PATCH DELETE are unsafe method as they update data
        // while doing the unsafe methods , then we need to pass csrf (cross side request forgery token) else csrf attack can be done

        // in server side the csrf has to be enabled and then the csrf that is passed from the frontend that is matched
        // into the server side by using the same algorithm used to match the csrf passed from frontend

        // here we disabled the csrf checking in server on hitting the UNSAFE methods like POST etc
        // still you have to authenticate to hit the other endpoints by either a authenticated SESSIONID or username and password

        // disabling this csrf
        http.csrf().disable().authorizeHttpRequests()
                // books
                .antMatchers(HttpMethod.POST,"/book/**").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE,"/book/**").hasAuthority("admin")
                .antMatchers(HttpMethod.PUT,"/book/**").hasAuthority("admin")
                .antMatchers(HttpMethod.GET,"/book/**").hasAnyAuthority("student","admin")
                // authors
                .antMatchers(HttpMethod.POST,"/author/**").hasAuthority("admin")
                .antMatchers(HttpMethod.GET,"/author/**").hasAnyAuthority("student","admin")
                // students
                .antMatchers(HttpMethod.POST , "/student/**").hasAnyAuthority("admin" , "student" )
                .antMatchers(HttpMethod.GET , "/student/**").hasAnyAuthority("admin" , "student" )
                .antMatchers(HttpMethod.DELETE , "/student/**").hasAuthority("student" )
                .antMatchers(HttpMethod.PUT , "/student/**").hasAuthority("student" )
                // txn
                .antMatchers( "/transaction/**").hasAnyAuthority("admin" , "student" )
                // admin
                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers("/**").permitAll()
                .and()
                .formLogin();

        // most restricted --> the least restricted
    }

    // selecting the encoder instance

    @Bean
    public PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }
}
