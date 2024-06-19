package com.demo.oauth2.config;



import com.demo.oauth2.filterAndTokenManager.JWTTokenFilter;
import com.demo.oauth2.service.LogoutService;
import com.demo.oauth2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {


    @Autowired
    UserService userService;


    @Autowired
    JWTTokenFilter jwtTokenFilter;

    @Autowired
    LogoutService logoutService;

    @Bean
    protected DaoAuthenticationProvider configure() throws Exception {

        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();

        // defines the type of authentication we want to have in app
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(encoderInstance());
        return auth;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers( "/authenticate", "/register" , "/refresh-token").permitAll()
                        .anyRequest().authenticated()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/").permitAll()
                )
//                .oauth2Login(Customizer.withDefaults())
                .cors(AbstractHttpConfigurer::disable)
                // adding a filter for jwt that intercepts the request
                // for checking the incoming request they have the correct bearer to access resources
                .addFilterBefore(jwtTokenFilter , UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(configure())
                // for logging out a user
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .addLogoutHandler(logoutService)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                //for using oauth2.0
                .oauth2Login(Customizer.withDefaults());


        return http.build();
    }


    PasswordEncoder encoderInstance(){
        return new BCryptPasswordEncoder();
        // by default the number of rounds are 10

    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

}