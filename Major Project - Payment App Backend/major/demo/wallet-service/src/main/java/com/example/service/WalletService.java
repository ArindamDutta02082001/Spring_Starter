package com.example.service;

import com.example.models.Wallet;
import com.example.repository.WalletRepository;
import com.example.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
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
        Map<String,Object> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        // creating a new wallet as soon as we create a new user
        Wallet wallet = Wallet.builder()
                .balance(10.0)
                .mobile((String) event.get("mobile"))
                        .build();

        walletRepository.save(wallet) ;
    }


    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    // consuming message from the transaction-service and updating the money in wallet of the user
    @KafkaListener(topics = "transaction_initiated", groupId = "random-id-it-is-needed-else-error") // partitions - 3, replication - 2
    public void update(String message) throws ParseException, JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        String sender = String.valueOf(event.get("sender"));
        String receiver = String.valueOf(event.get("receiver"));
        Double amount = (Double) event.get("amount");
        String externalTxnId = String.valueOf(event.get("externalTxnId")); // required

        // finding the sender and the receiver wallet
        Wallet senderWallet = this.walletRepository.findByMobile(sender);
        Wallet receiverWallet = this.walletRepository.findByMobile(receiver);

        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("receiver", receiver);
        responseMessage.put("sender", sender);
        responseMessage.put("amount", amount);
        responseMessage.put("externalTxnId", externalTxnId);

        if(senderWallet == null || receiverWallet == null || senderWallet.getBalance() < amount){
           System.out.println("Wallets will not be updated as the constraints failed");
            responseMessage.put("walletUpdateStatus", "FAILED");
            kafkaTemplate.send("wallet_updated", objectMapper.writeValueAsString(responseMessage));
            return;
        }

        try {

            // updating the amount in receiver and donor wallet
            walletRepository.updateWallet(sender, -amount);
            walletRepository.updateWallet(receiver, amount);


            responseMessage.put("walletUpdateStatus", "SUCCESS");
            kafkaTemplate.send("wallet_updated", objectMapper.writeValueAsString(responseMessage));
        }catch (Exception e){
            responseMessage.put("walletUpdateStatus", "FAILED");
            kafkaTemplate.send("wallet_updated", objectMapper.writeValueAsString(responseMessage));
        }


    }
}
