package com.example.controllers;

import com.example.dto.createTransactionDto;
import com.example.models.TransactionSecuredUser;
import com.example.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        TransactionSecuredUser user = (TransactionSecuredUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transactionService.initiate(user.getUsername(), request);
    }

}
