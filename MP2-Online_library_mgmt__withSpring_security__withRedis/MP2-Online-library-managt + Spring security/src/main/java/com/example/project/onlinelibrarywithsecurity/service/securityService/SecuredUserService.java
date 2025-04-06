package com.example.project.onlinelibrarywithsecurity.service.securityService;


import com.example.project.onlinelibrarywithsecurity.models.securitymodels.SecuredUser;
import com.example.project.onlinelibrarywithsecurity.repository.securityRepository.SecuredUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
// by default user details service coming form spring security
public class SecuredUserService implements UserDetailsService {

    @Autowired
    SecuredUserRepository securedUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.securedUserRepository.findByUsername(username);
    }

    public void saveUserinDB(SecuredUser securedUser)
    {
        this.securedUserRepository.save(securedUser);
    }
}
