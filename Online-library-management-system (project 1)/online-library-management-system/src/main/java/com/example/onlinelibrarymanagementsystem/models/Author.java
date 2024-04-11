package com.example.onlinelibrarymanagementsystem.models;

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

    // Bi-linking Book table to author table
    // Book --> Author
    // N : 1

    // in Book.java
    // @ManyToOne

    // in Author.java
    //  @OneToMany(mappedBy = "author")

//  @JsonManagedReference
    @JsonIgnoreProperties({"authored_by"})
    @OneToMany(mappedBy = "authored_by")
    private List<Book> bookList;


}
