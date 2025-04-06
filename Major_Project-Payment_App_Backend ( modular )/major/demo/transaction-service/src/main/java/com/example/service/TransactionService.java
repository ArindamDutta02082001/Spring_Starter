package com.example.service;

import com.example.dto.request.createTransactionDto;
import com.example.dto.response.userResponseDto;
import com.example.models.Transaction;
import com.example.models.TransactionSecuredUser;
import com.example.models.enums.TransactionStatusEnums;
import com.example.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;

@Service
public class TransactionService implements UserDetailsService {


    @Autowired
    TransactionRepository transactionRepository;




    // initiating a new transaction and pushing it to a transaction_initiated topic
    // it will be consumed by wallet-service to update specific amount to the sender & receiver wallet
    public String initiate(String sender, createTransactionDto request) throws JsonProcessingException {

        Transaction transaction = Transaction.builder()
                .externalTxnId(UUID.randomUUID().toString())
                .sender(sender)
                .receiver(request.getReceiver())
                .purpose(request.getPurpose())
                .amount(request.getAmount())
                .transactionStatus(TransactionStatusEnums.PENDING)          // it is in pending state
                .build();

        transactionRepository.save(transaction);

        ObjectMapper objectMapper = new ObjectMapper();
        kafkaTemplate.send( "transaction_initiated" , objectMapper.writeValueAsString(transaction));

        return transaction.getExternalTxnId();
    }



    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    // consuming the message from the wallet_update and saving that transaction as SUCCESS or FAILED
    // as initially the transaction state in the above is PENDING
    @KafkaListener(topics = "wallet_updated", groupId = "random-id-it-is-needed-else-error")
    public void updateTxn(String message) throws ParseException, JsonProcessingException {

        // consume the message and forming the map of objects from the JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        String sender = String.valueOf(event.get("sender"));
        String receiver = String.valueOf(event.get("receiver"));
        String walletUpdateStatus = String.valueOf(event.get("walletUpdateStatus"));
        String externalTxnId = String.valueOf(event.get("externalTxnId"));
        Double amount = (Double) event.get("amount");

        TransactionStatusEnums transactionStatus = walletUpdateStatus.equals("FAILED") ? TransactionStatusEnums.FAILED : TransactionStatusEnums.SUCCESSFUL;
        this.transactionRepository.updateTxnStatus(externalTxnId, transactionStatus);


        // pushing the transaction status to the transaction-complete-and-notify topic
        // this topic is not consumed by any service
        Transaction transaction = this.transactionRepository.findByExternalTxnId(externalTxnId);

        System.out.println(transaction);

        kafkaTemplate.send("transaction-complete-and-notify", objectMapper.writeValueAsString(transaction));

    }





    // ONLY this user will only be authenticated to use the transaction-service endpoints
    @Override
    public UserDetails loadUserByUsername(String mainmobile) throws UsernameNotFoundException {

        String url = "http://localhost:4000/user/mobile/"+mainmobile;


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        userResponseDto responseUser = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                userResponseDto.class
        ).getBody();

        if(responseUser == null)
            return null;

        System.out.println(responseUser.getAuthorities());

        TransactionSecuredUser transactionSecuredUser = TransactionSecuredUser.builder()
                .username(responseUser.getName())
                .password(responseUser.getPassword())
                .authorities(responseUser.getAuthorities().toString())
                .build();

        return transactionSecuredUser;


    }

    // getting all the transaction from a phone number
    public List<Transaction> getAllTransaction(String mobile)
    {
        return transactionRepository.getAllTransactionFromDb(mobile);
    }
}
