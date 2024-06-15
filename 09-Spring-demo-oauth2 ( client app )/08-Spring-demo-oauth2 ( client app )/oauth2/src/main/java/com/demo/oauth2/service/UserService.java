package com.demo.oauth2.service;

import com.demo.oauth2.dto.createUserDto;
import com.demo.oauth2.models.DemoUser;
import com.demo.oauth2.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public void createUser(createUserDto createUserDto)
    {
        DemoUser demoUser = DemoUser.builder()
            .name(createUserDto.getName())
            .email(createUserDto.getEmail())
            .username(createUserDto.getUsername())
            .password(new BCryptPasswordEncoder().encode(createUserDto.getPassword()))
            .authorities("user")
            .build();
        userRepository.save(demoUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userRepository.findByUsername(username);
        return userDetails;

    }
}
