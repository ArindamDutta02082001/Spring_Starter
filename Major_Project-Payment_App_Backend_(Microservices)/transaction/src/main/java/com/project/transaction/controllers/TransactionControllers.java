package com.project.transaction.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.project.transaction.dto.request.createTransactionDto;
import com.project.transaction.models.Transaction;
import com.project.transaction.models.TransactionSecuredUser;
import com.project.transaction.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/txn-history/{sender-mobile}")
    public List<Transaction> allTransactionDone(@PathVariable("sender-mobile") String mobile)
    {
        return transactionService.getAllTransaction(mobile);
    }

}
