package com.example.project.onlinelibrarywithsecurity.models;


import com.example.project.onlinelibrarywithsecurity.models.enums.Genre;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @JoinColumn -  it makes the attribute to act as a foreign key for another table
 *          and creates a column in current table of the primary key of the other table
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private Genre genre;

    private Integer pages;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    // linking Book Entity to Author Entity
    // Book --> Author
    // N : 1


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @ManyToOne
    @JoinColumn
    private Author authored_by;

    // linking Book Entity to Student Entity
    // Book --> Student
    // N : 1

    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @ManyToOne
    @JoinColumn
    private Student my_student;

    // Reverse-linking Book Entity to Transaction Entity
    // Book --> Transaction
    // 1 book can have : N transactions

    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @OneToMany(mappedBy = "books_in_trans_table")
    private List<Transaction> transactionList_B ;


}
