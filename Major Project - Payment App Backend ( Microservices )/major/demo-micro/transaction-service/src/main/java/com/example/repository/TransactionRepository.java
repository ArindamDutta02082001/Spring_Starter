package com.example.repository;

import com.example.models.Transaction;
import com.example.models.enums.TransactionStatusEnums;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {


    Transaction findByExternalTxnId(String externalTxnId);


    @Modifying
    @Query("update Transaction t set t.transactionStatus = ?2 where t.externalTxnId = ?1")
    void updateTxnStatus(String externalTxnId, TransactionStatusEnums transactionStatus);


    @Query("select t from Transaction t where t.sender = ?1")
    public List<Transaction> getAllTransactionFromDb(String senderMobile );




}
