package com.example.onlinelibrarymanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String contact;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    // Reverse-linking Book Entity to Student Entity
    // Book --> Student
    // N : 1


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @OneToMany(mappedBy = "my_student")
    private List<Book> bookList_S;

    // Reverse-linking Book Entity to Transaction Entity
    // Book --> Transaction
    // 1 : N


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @OneToMany(mappedBy = "students_in_trans_table")
    private  List<Transaction> transactionList_S ;

    Student()
    {}
}
