package com.example.onlinelibrarymanagementsystem.models;

import com.example.onlinelibrarymanagementsystem.models.enums.TransactionStatus;
import com.example.onlinelibrarymanagementsystem.models.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @Join -  it makes the attribute to act as a foreign key for another table
 *          and creates a column in current table of the primary key of the other table
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String externalTxnId;
    // we will show this transaction id to the outside user instead of the id ( PK )

    @CreationTimestamp
    private Date transactionTime;

    @UpdateTimestamp
    private Date updatedOn;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    private Double fine;

    // Bi-linking Transaction Entity to Book Entity
    // Transaction --> Book
    // N : 1

    // in Transaction.java
    // @ManyToOne

    // in Book.java
    // @OneToMany(mappedBy = "student")

    @ManyToOne
    @JoinColumn
    private Book book;

    // same for studnt
    @ManyToOne
    @JoinColumn
    private Student student;


    Transaction()
    {

    }



}
