package com.example.controllers;

import com.example.dto.createTransactionDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionControllers {

    @PostMapping("/initiate-txn")
    public String initiateTxn(@Valid @RequestBody createTransactionDto request) throws JsonProcessingException {
        TxnUser user = (TxnUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return txnService.initiate(user.getUsername(), request);
    }

}
