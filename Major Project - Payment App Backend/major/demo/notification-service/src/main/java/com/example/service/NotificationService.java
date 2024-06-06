package com.example.service;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Service
public class NotificationService {

    private static Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    JavaMailSender javaMailSender;



    @KafkaListener(topics = "transaction-complete-and-notify", groupId = "random-id-it-is-needed-else-error")
    public void sendNotif(String message) throws ParseException, JsonProcessingException {

        //TODO: SEND EMAILS

        logger.info("msg received = {}", message);


        // consume the message and forming the map of objects from the JSON Object String
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> event = new HashMap<>();
        event = objectMapper.readValue(message ,Map.class);

        String sender = String.valueOf(event.get("sender"));
        String receiver = String.valueOf(event.get("receiver"));
        String externalTxnId = String.valueOf(event.get("externalTxnId"));
        Double amount = (Double) event.get("amount");
        String transactionStatus = String.valueOf(event.get("transactionStatus"));

        if(!transactionStatus.equals("FAILED")){
            String receiverMsg = "Hi! Your account has been credited with amount " + amount + "for the transaction done by " + sender;
            simpleMailMessage.setTo("duttaarindam902@gmail.com");
            simpleMailMessage.setSubject("your transaction update : ");
            simpleMailMessage.setFrom("arindamdutta02082001@gmail.com");
            simpleMailMessage.setText(receiverMsg);
            javaMailSender.send(simpleMailMessage);
        }

        String senderMsg = "Hi! Your transaction " + externalTxnId + " of amount " + amount + " has been " + transactionStatus;
        simpleMailMessage.setTo("duttaarindam902@gmail.com");
        simpleMailMessage.setSubject("your transaction update : ");
        simpleMailMessage.setFrom("arindamdutta02082001@gmail.com");
        simpleMailMessage.setText(senderMsg);
        javaMailSender.send(simpleMailMessage);

    }
}

