package com.example.demospringsecurity.database.config;

import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/*

     @EnableWebSecurity  is deprecated so we will use FilterChain

 */


@Configuration
@EnableWebSecurity
public class SecurityconfigInDB{


    // the AuthenticationManagerBuilder decides what type of authentication is to be provided
    // In database authentication
    // In this , there is a repository class that manages to store the user details entered into it


    // after authentication the authorized personnel can use the endpoints

    @Autowired
    UserService userService;



    // Database authentication
    // In this , the DaoAuthenticationProvider decides what type of authentication is to be provided
    // Here we are using user service for DB auth so we aren`t hard coding the user credentials


    @Bean
    protected DaoAuthenticationProvider configure() throws Exception {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        // defines the type of authentication we want to have in app
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(encoderInstance());
        return auth;
    }

    // OLD
    /*
        @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.userDetailsService(userService);
    }
     */


    // Authorization related stuff - after the user is authenticated , we are authorizing
    // the user which endpoint he/she has the access


    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/faculty/attendance/**").hasAuthority("admin")
                        .requestMatchers("/credential/**").hasAnyAuthority("admin")
                        .requestMatchers("/faculty/**").hasAuthority("faculty")
                        .requestMatchers("/student/**").hasAuthority("student")
                        .requestMatchers("/library/**").hasAnyAuthority("student", "faculty")
                        .requestMatchers("/home").permitAll()
                        .requestMatchers("/shop").permitAll()
                        .requestMatchers("/usersignup").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .rememberMe(Customizer.withDefaults());

        // most restricted --> least restricted

        return http.build();
    }

    // OLD
    /*
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
     */


    // selecting the encoder instance
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }

}
