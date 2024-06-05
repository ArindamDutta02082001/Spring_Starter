package com.example.controllers;

import com.example.dto.createTransactionDto;
import com.example.models.TransactionSecuredUser;
import com.example.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionControllers {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiate-txn")
    public String initiateTxn(@Valid @RequestBody createTransactionDto request) throws JsonProcessingException {
        Object user = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println("info"+objectMapper.writeValueAsString(user));
        System.out.println("info3"+objectMapper.writeValueAsString(transactionService.loadUserByUsername("9620922432")));
        return transactionService.initiate("9620922432", request);
    }

}
