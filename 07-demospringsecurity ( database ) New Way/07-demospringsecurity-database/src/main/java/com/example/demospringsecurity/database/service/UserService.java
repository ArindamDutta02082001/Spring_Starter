package com.example.demospringsecurity.database.service;

import com.example.demospringsecurity.database.model.User;
import com.example.demospringsecurity.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

// responsible  for retrieving the data from the DB
@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    // to get a user by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username);
    }


    // to save a user in user table
    public void saveFromDB(User demoUser){
        this.userRepository.save(demoUser);
    }

    // to get all users
    public List<User> getAllUsersFromDB()
    {
        return this.userRepository.findAll();
    }


    // deleting the user
    public void deleteUserFromDB(int id)
    {
        this.userRepository.deleteById(id);
    }

}
