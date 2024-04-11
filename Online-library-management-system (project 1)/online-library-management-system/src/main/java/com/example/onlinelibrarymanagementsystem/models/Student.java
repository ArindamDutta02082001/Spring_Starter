package com.example.onlinelibrarymanagementsystem.models;

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

    // in Book.java
    // @ManyToOne

    // in Student.java
    //  @OneToMany(mappedBy = "my_student")

    @OneToMany(mappedBy = "my_student")
    private List<Book> bookList;

    // Reverse-linking Book Entity to Transaction Entity
    // Book --> Transaction
    // 1 : N

    // in Book.java
    // @ManyToOne

    // in Transaction.java
    //  @OneToMany(mappedBy = "my_student_transaction")
    @OneToMany(mappedBy = "my_student_transaction")
    private  List<Transaction> transactionList ;

    Student()
    {}
}
