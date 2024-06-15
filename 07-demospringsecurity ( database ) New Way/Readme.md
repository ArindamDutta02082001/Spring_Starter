# This is a SpringBoot Web Security [ In Database ] NEW WAY

Every thing remains the same as 07 project only we will omit the
* `WebSecurityConfigurerAdapter` omitted
* `2 configure methods` not required as we will use custom @Bean methods
* `AuthenticationManagerBuilder` replaced by `DaoAuthenticationProvider`
* `antMatchers`  replaced by `requestMatchers`
* `.csrf().disable()` replaced by `.csrf(csrf -> csrf.disable())`
* `.and()`
as these are deprecated after Spring 3.0 & Spring Security > 5.0

##### Modification needed in the SecurityConfig.java file
- we introduce the concept of `SecurityFilterChain`
```dockerfile
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/faculty/attendance/**").hasAuthority("admin")
                        .requestMatchers("/credential/**").hasAnyAuthority("admin")
                        .requestMatchers("/faculty/**").hasAuthority("faculty")
                        .requestMatchers("/student/**").hasAuthority("student")
                        .requestMatchers("/library/**").hasAnyAuthority("student", "faculty")
                        .requestMatchers("/home","/shop","/usersignup" , "/error").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .permitAll()
                )
                .authenticationProvider(configure());

        // most restricted --> least restricted

        return http.build();
    }
```

### API endpoints are here
same endpoints like in previous 07 project

### application.properties file
same DB like in previous 07 project