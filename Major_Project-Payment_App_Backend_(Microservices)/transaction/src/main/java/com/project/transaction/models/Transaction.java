package com.project.transaction.models;


import com.project.transaction.models.enums.TransactionStatusEnums;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalTxnId;

    private String sender; // sender's phone number

    private String receiver; // receiver's phone number

    private Double amount;

    private String purpose;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatusEnums transactionStatus;

    @CreationTimestamp
    private Date transactionTime;

    @UpdateTimestamp
    private Date updatedOn;
}
