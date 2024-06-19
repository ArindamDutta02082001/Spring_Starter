package com.example.service;

import com.example.cacheResponseObject.UserCache;
import com.example.dto.createUserDto;
import com.example.models.User;
import com.example.repository.UserCacheRepository;
import com.example.repository.UserRepository;
import com.example.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserCacheRepository userCacheRepository;



    // create & save  a new User
    public User create(createUserDto createUserDto) throws JsonProcessingException {
        User user = User.builder()
                .name(createUserDto.getName())
                .password(new BCryptPasswordEncoder().encode(createUserDto.getPassword()))     //TODO: Remove from here and make only one single point of contact for pe related changes
                .mobile(createUserDto.getMobile())
                .email(createUserDto.getEmail())
                .password(createUserDto.getPassword())
                .authorities(Constants.USER_AUTHORITY)
                .build();

        userRepository.save(user);

        // Communicate to wallet-service to create a user's wallet
        // Ex: Wallet creation takes ~ 3-5 seconds, you don't want your users to wait till that time
        // asynchronously via kafka


        // Serialize the User object to JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        String userJson = objectMapper.writeValueAsString(user);
        kafkaTemplate.send(Constants.USER_CREATED_TOPIC, userJson);
    return user;
    }

    public UserCache getUserByUserId(int userId) {
        UserCache user = (UserCache) this.userCacheRepository.getFromCache(userId);
        if(user == null)
        {
           User user1 = this.userRepository.findById(userId).orElse(null);
           if(user1 != null) {
               UserCache userCache = UserCache.builder()
                       .updatedOn(user1.getUpdatedOn())
                       .createdOn(user1.getCreatedOn())
                       .authorities(user1.getAuthorities().toString())
                       .email(user1.getEmail())
                       .mobile(user1.getMobile())
                       .userId(user1.getUserId())
                       .password(user1.getPassword())
                       .name(user1.getName())
                       .build();

               this.userCacheRepository.saveInCache(userCache);
               return userCache;
           }
        }

        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        return this.userRepository.findByMobile(mobile);
    }
}
