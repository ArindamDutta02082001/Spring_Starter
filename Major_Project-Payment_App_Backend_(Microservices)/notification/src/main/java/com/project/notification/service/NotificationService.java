package com.project.notification.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {


    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    RestTemplate restTemplate;


    // get mails of the sender and the receiver
    public String getEmails(String mobile)
    {
        String url = "http://user-service:4000/user/mobile/"+mobile;


        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> request = new HttpEntity<>(headers);
        ResponseEntity<String> responseUser = restTemplate.exchange(
                url,
                HttpMethod.GET,
                request,
                String.class
        );
        System.out.println("info2"+responseUser.getBody());
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object>   userData = null;
        try {
            userData = objectMapper.readValue(responseUser.getBody(), Map.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        };

        return (String) userData.get("email");
    }


    @KafkaListener(topics = "wallet_updated", groupId = "random-id-it-is-needed-else-error")
    public void sendNotification(String message) throws ParseException, JsonProcessingException {

        System.out.println("Message from topic : " + message);


        // consume the message and forming the map of objects from the JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        String sender = String.valueOf(event.get("sender"));
        String receiver = String.valueOf(event.get("receiver"));
        String externalTxnId = String.valueOf(event.get("externalTxnId"));
        Double amount = (Double) event.get("amount");
        String transactionStatus = String.valueOf(event.get("walletUpdateStatus"));
        System.out.println(transactionStatus);

        // to get the emails of the sender and the receiver
        String senderEmail = getEmails(sender);
        String receiverEmail = getEmails(receiver);
        System.out.println("Emails : "+senderEmail + "&&" + receiverEmail);

        if(transactionStatus.equals("FAILED"))
        {

            // if txn fails the sender gets notifies
            String senderMssg = "Hi! Your account has been credited with amount " + amount + " was Unsuccessful ! ";
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(senderEmail);
            simpleMailMessage.setSubject("Transaction update : ");
            simpleMailMessage.setFrom("arindamdutta1970@gmail.com");
            simpleMailMessage.setText(senderMssg);
            javaMailSender.send(simpleMailMessage);
            return ;
        }

        // if txn is success both the sender & receiver gets notified
        // for sender
        String senderMsg = "Hi! Your transaction " + externalTxnId + " of amount " + amount + " is DEBITED has been " + transactionStatus;
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(senderEmail);
        simpleMailMessage.setSubject("Transaction update : ");
        simpleMailMessage.setFrom("arindamdutta1970@gmail.com");
        simpleMailMessage.setText(senderMsg);
        javaMailSender.send(simpleMailMessage);

        // for receiver
        String receiverMsg = "Hi! Your transaction " + externalTxnId + " of amount " + amount + " is CREDITED has been " + transactionStatus;
        simpleMailMessage.setTo(receiverEmail);
        simpleMailMessage.setSubject("Transaction update : ");
        simpleMailMessage.setFrom("arindamdutta1970@gmail.com");
        simpleMailMessage.setText(receiverMsg);
        javaMailSender.send(simpleMailMessage);

    }
}

