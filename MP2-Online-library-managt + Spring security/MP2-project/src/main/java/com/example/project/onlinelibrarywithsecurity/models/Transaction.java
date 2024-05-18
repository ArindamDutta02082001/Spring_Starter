package com.example.project.onlinelibrarywithsecurity.models;

import com.example.project.onlinelibrarywithsecurity.models.enums.TransactionStatus;
import com.example.project.onlinelibrarywithsecurity.models.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
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
    @GeneratedValue( strategy = GenerationType.IDENTITY )
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

    // linking Transaction Entity to Book Entity
    // Transaction --> Book
    // N transaction can be performed for : 1 Book


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @ManyToOne
    @JoinColumn
    private Book books_in_trans_table;

    // linking Transaction Entity to Student Entity
    // Transaction --> Student
    // N transaction can be performed by : 1 Student


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @ManyToOne
    @JoinColumn
    private Student students_in_trans_table;


    public Transaction()
    {
    }


}
