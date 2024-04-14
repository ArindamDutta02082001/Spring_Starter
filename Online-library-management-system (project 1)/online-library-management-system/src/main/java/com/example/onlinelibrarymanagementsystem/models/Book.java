package com.example.onlinelibrarymanagementsystem.models;

import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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

    @JsonIgnoreProperties({"bookList"})
    @ManyToOne
    @JoinColumn
    private Author authored_by;

    // linking Book Entity to Student Entity
    // Book --> Student
    // N : 1

    @ManyToOne
    @JoinColumn
    private Student my_student;

    // Reverse-linking Book Entity to Transaction Entity
    // Book --> Transaction
    // 1 book can have : N transactions

    @OneToMany(mappedBy = "my_book_transaction")
    private List<Transaction> transactionList ;


}
