package com.example.onlinelibrarymanagementsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    private String authorName;

    private String country;

    @CreationTimestamp
    private Date addedOn;

    // Linking Book table to author table
    // Book --> Author
    // N : 1

    // in Book.java
    // @ManyToOne

    // in Author.java
    //  @OneToMany(mappedBy = "author")


    @JsonIgnoreProperties({"books_in_trans_table", "bookList_S","bookList_A","authored_by" ,"students_in_trans_table", "my_student","transactionList_B","transactionList_S" })
    @OneToMany(mappedBy = "authored_by")
    private List<Book> bookList_A;


}
