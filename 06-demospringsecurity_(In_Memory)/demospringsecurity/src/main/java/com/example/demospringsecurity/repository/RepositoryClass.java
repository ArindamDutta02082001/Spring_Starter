package com.example.demospringsecurity.repository;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Repository;


@Repository
public class RepositoryClass {

    /**
     * the repository file contains the code of DB connection and data storage
     */

    /*
     you need to make sure that the data present in memory should be in this format

     arindam@123 - $2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W | student
     ram@123 - $2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq | faculty
     ayush@123 - $2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6   | admin

     */
    RepositoryClass()
    {
        inMemoryUserDetailsManager.createUser(User.builder().username("arindam")
                .password("$2a$10$P68A3Wf2H6nES9OkXWZoj.CakfPbEoh1VDNEueXDjBNsUNZysU43W")
                .authorities("student")
                .build());
        inMemoryUserDetailsManager.createUser(User.builder().username("ram")
                .password("$2a$10$b.6RmGDoZ6O12h8QRaShxeA9ckO6yuVMQJYZFHVt6fSzrC.Mo2gNq")
                .authorities("faculty")
                .build());
        inMemoryUserDetailsManager.createUser(User.builder().username("ayush")
                .password("$2a$10$s1Cp0dps2uDYG9SWXBdivOXyFqBmN4YmrNy70qrWlSqoh7v7.BWG6")
                .authorities("admin")
                .build());
    }

    public InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();

    public InMemoryUserDetailsManager createInMemoryUserDetailManager()
    {
        return this.inMemoryUserDetailsManager;
    }

    public void saveUserDetailsinMemory(UserDetails userDetails)
    {
        this.inMemoryUserDetailsManager.createUser(userDetails);
    }

    public UserDetails getUserDetailinMemory(String username)
    {
        return this.inMemoryUserDetailsManager.loadUserByUsername(username);
    }



}
