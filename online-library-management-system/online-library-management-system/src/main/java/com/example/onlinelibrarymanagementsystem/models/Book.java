package com.example.onlinelibrarymanagementsystem.models;

import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
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

    // Bi-linking Book Entity to Author Entity
    // Book --> Author
    // N : 1

    // in Book.java
    // @ManyToOne

    // in Author.java
    //  @OneToMany(mappedBy = "author")

    @ManyToOne
    @JoinColumn
    private Author author;

    // Bi-linking Book Entity to Student Entity
    // Book --> Tra
    // N : 1

    // in Book.java
    // @ManyToOne

    // in Student.java
    //  @OneToMany(mappedBy = "student")

    @ManyToOne
    @JoinColumn
    private Student my_student;

    Book()
    {}

}
