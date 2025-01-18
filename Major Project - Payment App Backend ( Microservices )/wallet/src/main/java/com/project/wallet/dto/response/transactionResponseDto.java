package com.project.wallet.dto.response;


import com.project.wallet.dto.response.enums.TransactionStatusEnums;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class transactionResponseDto {


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
