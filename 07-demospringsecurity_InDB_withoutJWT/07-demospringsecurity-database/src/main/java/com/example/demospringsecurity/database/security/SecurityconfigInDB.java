package com.example.demospringsecurity.database.security;


import com.example.demospringsecurity.database.security.filters.CustomJSONAuthFilter;
import com.example.demospringsecurity.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;


@Configuration
public class SecurityconfigInDB{


    /**
     *
     note : If you have a service that implements UserDetailsService , and a user entity class
     that  implements UserDetails  , you dont need to create custom beans of Service and
     authentication Manager ( by registering the custom auth provider , password encoder and service inside it)

     custom beans of Service and authentication Manager is required only
     1. you implement your custom Service ( not implements UserDetailsService  ) and User ( not implements UserDetails )
     2. Create custom Filters and add it to the filterchain ( as inside teh filter we neeed the )
     */

    /**
     Authorization related stuff - after the user is authenticated , we are authorizing based on roles
     i.e the user which endpoint he/she has the access
     also the path mapping should be from : most restricted --> least restricted
     */

    @Autowired
    UserService userService;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http ) throws Exception {

        // you have to create the custom filters objects here
//        CustomJSONAuthFilter customJSONAuthFilter = new CustomJSONAuthFilter(authenticationManager(http));

        http
                .csrf(csrf -> csrf.disable())
                // for any unsafe method like POST , PUT  , DELETE , PATCH we have to pass a csrf token to the server else csrf attack may occur.
                // for GET endpoint csrf token from frontend is not required . since we don`t have the frontend which will not generate a csrf token
                // we are disabling it here (very very UNSAFE method don`t do it)
                // disabling csrf doesn`t mean that we don`t have to provide username and password in login
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/home","/shop","/usersignup" , "/error" , "/login" ).permitAll()
                        .requestMatchers("/faculty/attendance/**").hasAuthority("admin")
                        .requestMatchers("/credential/**").hasAnyAuthority("admin")
                        .requestMatchers("/faculty/**").hasAuthority("faculty")
                        .requestMatchers("/student/**").hasAuthority("student")
                        .requestMatchers("/library/**").hasAnyAuthority("student", "faculty")
                        .anyRequest().authenticated()
                )
//                .addFilterBefore(customJSONAuthFilter, UsernamePasswordAuthenticationFilter.class)
            // enabling filter JSON body based


                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )

            // enabling form based
        ;

        // most restricted --> least restricted

        return http.build();
    }


    /** enabling these beans as if you comment out line line 50 & 68 , our custom filter will get activated
    // enabling passing username and password in JSON body
    //{
    //    "username": "ram",
    //    "password": "ram@123"
    //}
     */

//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userService);
//        authProvider.setPasswordEncoder(encoderInstance());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
//        return new ProviderManager(Arrays.asList(authenticationProvider()));
//    }

    // selecting the password encoder instance
    @Bean
    public PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();   // by default the number of rounds are 10
    }
















    /**    OLD WAY we used to do

     first configure method for authentication manager

          @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // defines the type of authentication we want to have in app
        auth.userDetailsService(userService);
    }

          second configure method for role authorization

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

        // selecting the password encoder instance
     @Bean
     PasswordEncoder encoderInstance(){
     return new BCryptPasswordEncoder();
     }


     */


}
