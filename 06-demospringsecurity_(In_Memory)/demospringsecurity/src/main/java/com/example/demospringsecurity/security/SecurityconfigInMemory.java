package com.example.demospringsecurity.security;

import com.example.demospringsecurity.repository.RepositoryClass;
import com.example.demospringsecurity.service.InmemoryService;
import jakarta.servlet.FilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/*

In new way we create few beans  ( not @override like the old way )
- AuthenticationManager
- PasswordEncoder
-



*/


//@EnableWebSecurity  - optional
@Configuration
@EnableWebSecurity
public class SecurityconfigInMemory {


    // not req
    @Autowired
    InmemoryService inmemoryService;

    // not req
    @Autowired
    RepositoryClass repositoryClass;



    /**
     *
     note : If you have a service that implements UserDetailsService , and a user entity class
     that  implements UserDetails  , you dont need to create custom beans of Service and
     authentication Manager ( by registering the custom auth provider , password encoder and service inside it)

     */

/**
 * this will hardcoded return a specific user to match
 * but this will not return since the InMemoryUserDetailsManager bean won't be created when this bean is created
hence return will always be null .

    @Bean
    public UserDetails userDetailsService() {
        return inmemoryService.getUserDetails("arindam");    --->   not work
    }

 */

/* this will hardcoded return a specific user to match */
//@Bean
//public UserDetails userDetailsServices() {
//    UserDetails user = User.withUsername("arindam")
//            .password(encoderInstance().encode("arindam@123"))
//            .roles("USER")
//            .build();
//return user;
//}

    // this will work as you are returning the entire service
    // not req
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return (UserDetailsService) inmemoryService;
//    }


    // not req
//    @Bean
//    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
//        return repositoryClass.createInMemoryUserDetailManager();
//    }


    // not req
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(InMemoryUserDetailsManager userDetailsService, PasswordEncoder passwordEncoder) {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }



    /*
     Authorization related stuff - after the user is authenticated , we are authorizing based on roles
     i.e the user which endpoint he/she has the access
     also the path mapping should be from : most restricted --> least restricted

 */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable())
//              .cors(cors -> someCorsConfig we can set to allow only certain Type of request ike GET PUT and other blocked)
                .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/faculty/attendance/**").hasAuthority("admin")
                .requestMatchers("/signup/**").hasAuthority("admin")
                .requestMatchers("/credential/**").hasAuthority("admin")
                .requestMatchers("/faculty/**").hasAuthority("faculty")
                .requestMatchers("/student/**").hasAuthority("student")
                .requestMatchers("/library/**").hasAnyAuthority("student", "faculty")
                .requestMatchers("/home").permitAll()
                .requestMatchers("/shop").permitAll()
                .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults());


        //                .and()   --> ensures that the functions coming after it are directly attached to the root , HttpClient
        //                .formLogin(); --> this ensure sto give 2 extra api endpoints for form login for authentication
        //                 .anyRequest().authenticated() --> user requesting for any other endpoint (has to be defined in the controller but hsa no authority mentioned)
        //                                                  will have to get authenticated
        //                 .requestMatchers("/**").permitAll() --> any user hitting this endpoint will be permitted i.e no auth req

        return http.build();
    }




    /*

    We have to create a PasswordEncoder Instance

     we made , a bean of BcryptEncoder - the passwords are hashed upto 10 rounds like below when matched or when stored in DB

     useful site - https://bcrypt-generator.com/

     arindam@123 - $2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W
     ram@123 - $2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq
     ayush@123 - $2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6


    */
    @Bean
    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();              // by default the number of rounds are 10

    }

    /*

     a normal noOpsEncoder instance - passwords are compared in raw string format

    @Bean
    PasswordEncoder encoderInstance()
    {
        return NoOpPasswordEncoder.getInstance();
    }

     */



}
