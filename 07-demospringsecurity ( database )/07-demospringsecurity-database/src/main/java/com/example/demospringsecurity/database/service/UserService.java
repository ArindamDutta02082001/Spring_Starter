package com.example.demospringsecurity.database.service;

import com.example.demospringsecurity.database.entity.User;
import com.example.demospringsecurity.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// responsible  for retrieving the data from the DB
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }

    public User save(User demoUser){
        return this.userRepository.save(demoUser);
    }
}
