package com.example.onlinelibrarymanagementsystem.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;

    private String authorName;

    private String country;

    @CreationTimestamp
    private Date addedOn;

    // Bi-linking Book table to author table
    // Book --> Author
    // N : 1

    // in Book.java
    // @ManyToOne

    // in Author.java
    //  @OneToMany(mappedBy = "author")

    @OneToMany(mappedBy = "author")
    private List<Book> bookList;

    Author()
    {}
}
