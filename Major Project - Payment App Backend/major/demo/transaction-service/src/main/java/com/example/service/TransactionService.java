package com.example.service;

import com.example.dto.createTransactionDto;
import com.example.models.Transaction;
import com.example.models.TransactionSecuredUser;
import com.example.models.enums.TransactionStatusEnums;
import com.example.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.DataInput;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TransactionService implements UserDetailsService {


    @Autowired
    TransactionRepository transactionRepository;





    // initiating a new transaction and pushing it to a kafka topic
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

    // consuming the message from the wallet_update and saving that transaction as successful or failed
    // as initially the transaction state in the above is PENDING


    @KafkaListener(topics = "wallet_update", groupId = "random-id-it-is-needed-else-error")
    public void updateTxn(String message) throws ParseException, JsonProcessingException {

        // consume the message and forming the map of objects from the JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,String> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        String sender = String.valueOf(event.get("sender"));
        String receiver = String.valueOf(event.get("receiver"));
        String walletUpdateStatus = String.valueOf(event.get("walletUpdateStatus"));
        String externalTxnId = String.valueOf(event.get("externalTxnId"));
        Double amount = Double.parseDouble(event.get("amount"));

//        TransactionStatus transactionStatus = walletUpdateStatus.equals("FAILED") ? TransactionStatus.FAILED : TransactionStatus.SUCCESSFUL;
//        this.txnRepository.updateTxnStatus(externalTxnId, transactionStatus);
//
//        Transaction transaction = this.txnRepository.findByExternalTxnId(externalTxnId);

        // TODO: Make an API call to user-service to fetch the email address of both sender and receiver
//        kafkaTemplate.send(Constants.TXN_COMPLETED_TOPIC, objectMapper.writeValueAsString(transaction));

    }


    RestTemplate restTemplate = new RestTemplate();


    @Override
    public UserDetails loadUserByUsername(String usermobile) throws UsernameNotFoundException {

        // this user will only be authenticated to use the transaction-service endpoints
        // fetch the user details
        String url = "http://localhost:4000/user/mobile/" + usermobile;

        // Creating authorization header for txn service
        String plainCreds = "txnservice:P@ass123";
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);  // error
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        HttpEntity<String> request = new HttpEntity<>(headers);

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object>   userData = null;
        try {
            userData = objectMapper.readValue((DataInput) request, Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return TransactionSecuredUser.builder()
                .username((String)userData.get("mobile"))
                .password((String) userData.get("password"))
                .build();

    }
}
