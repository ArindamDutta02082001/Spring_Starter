package com.example.demospringsecurity.service;

import com.example.demospringsecurity.repository.RepositoryClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class InmemoryService {

    @Autowired
    RepositoryClass repositoryClass ;


    // to interact with the  InMemoryUserDetailManager in the repository class
    public InMemoryUserDetailsManager getInMemoryUserDetailManager()
    {
        return repositoryClass.createInMemoryUserDetailManager();
    }

    // to save a particular employee in the DB
    public void saveUserDetails(UserDetails userDetails)
    {
        repositoryClass.saveUserDetailsinMemory(userDetails);

    }

    // to get a particular employee information from the IN memory DB
    public UserDetails getUserDetails(String username)
    {
        return repositoryClass.getUserDetailinMemory(username);
    }


    // to

}
