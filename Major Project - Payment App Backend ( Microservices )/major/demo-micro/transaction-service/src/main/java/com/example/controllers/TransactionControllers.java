package com.example.controllers;

import com.example.dto.request.createTransactionDto;
import com.example.models.TransactionSecuredUser;
import com.example.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/txn")
public class TransactionControllers {
    @Autowired
    TransactionService transactionService;

    @PostMapping("/initiate-txn/{sender-mobile}")
    public String initiateTxn(@PathVariable("sender-mobile") String mobile, @Valid @RequestBody createTransactionDto request) throws JsonProcessingException {

        TransactionSecuredUser transactionSecuredUser = (TransactionSecuredUser) transactionService.loadUserByUsername(mobile);

        if( transactionSecuredUser == null || Objects.equals(mobile, request.getReceiver()))
            return "give proper sender phn no to transact";


        return transactionService.initiate(mobile, request);
    }


}
