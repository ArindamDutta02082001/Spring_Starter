package com.project.wallet.repository;


import com.project.wallet.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByMobile(String mobile);


    // we are taking mobile as it is unique for different users we assume
    @Modifying
    @Query("update Wallet w set w.balance = w.balance + :amount where w.mobile = :mobile")
    void updateWallet(String mobile, Double amount);

    // to get the balance of a particular phone number
    @Query("select w.balance from Wallet w where w.mobile = :mobile")
    String getBalanceFromDB(String mobile);
}
