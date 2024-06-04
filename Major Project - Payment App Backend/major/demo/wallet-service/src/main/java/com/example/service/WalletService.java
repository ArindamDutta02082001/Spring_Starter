package com.example.service;

import com.example.models.Wallet;
import com.example.repository.WalletRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepository;



    // consuming message from user_created topic of new user creation
    @KafkaListener(topics = "user_created", groupId = "random-id-it-is-needed-else-error")
    public void consumeKafkaEvent(String message) throws JsonProcessingException {

        // consume the message
        // we can see if we create a new user in the user-service , that new message is consumed and printed here
        System.out.println("Received message: " + message);


        // consume the message and forming the map of objects from the JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        // creating a new wallet as soon as we create a new user
        Wallet wallet = Wallet.builder()
                .balance(10.0)
                .mobile((String) event.get("mobile"))
                        .build();

        walletRepository.save(wallet) ;
    }



    // producing message
}
