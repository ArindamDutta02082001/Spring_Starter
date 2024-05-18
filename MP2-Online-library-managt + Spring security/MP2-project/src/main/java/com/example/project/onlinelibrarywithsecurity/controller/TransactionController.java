package com.example.project.onlinelibrarywithsecurity.controller;


import com.example.project.onlinelibrarywithsecurity.models.Transaction;
import com.example.project.onlinelibrarywithsecurity.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    // get all transaction upon a GET call
    @GetMapping("/")
    public List<Transaction> allTransactions()
    {
        return transactionService.getAllTransactionfromDB();
    }

    // initiate a transaction to order books
    @PostMapping("/issue")
    public String issueTxn(@RequestParam("name") String name, @RequestParam("studentId") int studentId) throws Exception {
        return transactionService.issueTxn(name, studentId);
    }

    // initiate a transaction to return books
    @PostMapping("/return")
    public String returnTxn(@RequestParam("name") String bookname, @RequestParam("studentId") int studentId) throws Exception {
        return transactionService.returnTxn(bookname, studentId);
    }
    /**
     * Issuance
     *  1. Get the book details and student details // here we are checking whether book is available or not
     *  2. Validation //
     *  3. Create a txn with pending status
     *  4. Assign the book to that particular student // update book set student_id = ? // here we are checking whether book is available or not
     *  5. Update the txn accordingly with status as SUCCESS or FAILED
     */


    /**
     * Return
     *  1. Create a txn with pending status
     *  2. Check the due date and correspondingly evaluate the fine
     *  3. Unassign the book from student // UPDATE BOOK table set student_id = null
     *  4. Update the txn accordingly with status as SUCCESS or FAILED
     */






}
