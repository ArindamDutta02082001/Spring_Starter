package com.project.wallet.controller;


import com.project.wallet.dto.response.transactionResponseDto;
import com.project.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    WalletService walletService;

    // get the amount present in the wallet of a user
    @GetMapping("/balance/{sender-mobile}")
    public String getAmountLeft(@PathVariable("sender-mobile") String mobile)
    {
        System.out.println("asdasd");
        return walletService.getBalance(mobile);
    }



    // get the wallet history i.e all the transaction histories

    @GetMapping("/wallet-history/{sender-mobile}")
    public List<transactionResponseDto> getAllTransactionHistory(@PathVariable("sender-mobile") String mobile)
    {
        return walletService.getTransactionHistory(mobile);
    }

}
