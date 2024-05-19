package com.example.project.onlinelibrarywithsecurity.service;


import com.example.project.onlinelibrarywithsecurity.models.SecuredUser;
import com.example.project.onlinelibrarywithsecurity.repository.SecuredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// by default userdetails service coming form spring security
public class SecuredUserService implements UserDetailsService {

    @Autowired
    SecuredUserRepository securedUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public void saveUserinDB(SecuredUser securedUser)
    {
        this.securedUserRepository.save(securedUser);
    }
}
