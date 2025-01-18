package com.demo.oauth2.service;


import com.demo.oauth2.dto.registerDto;
import com.demo.oauth2.models.DemoUser;
import com.demo.oauth2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public DemoUser createUser(registerDto createUserDto)
    {
        DemoUser demoUser = DemoUser.builder()
            .name(createUserDto.getName())
            .email(createUserDto.getEmail())
            .username(createUserDto.getUsername())
            .password(new BCryptPasswordEncoder().encode(createUserDto.getPassword()))
            .authorities("user")
            .build();
        userRepository.save(demoUser);
        return demoUser;
    }

    public String changePassword(String newPassword , String confirmNewPassword , String username)
    {
        if(newPassword != confirmNewPassword)
            return "Password mismatch";
        else
            userRepository.changePassword(new BCryptPasswordEncoder().encode(newPassword) , username);

        return "Password changed successfully";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = userRepository.findByUsername(username);
        return userDetails;

    }
}
