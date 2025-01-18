package com.project.transaction.config;



import com.project.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig  {

    @Autowired
    TransactionService transactionService;


    @Bean
    protected DaoAuthenticationProvider configure( ) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPE());
        daoAuthenticationProvider.setUserDetailsService(transactionService);
        return daoAuthenticationProvider;
    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.
//                httpBasic()
//                .and()
//                .csrf()
//                .disable()
//                .authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> cors.disable())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->
                        authorizationManagerRequestMatcherRegistry
                                .anyRequest().permitAll()
                ).formLogin(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }





    @Bean
    PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }
}