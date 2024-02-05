package com.example.bookmanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @Entity - is a JPA annotation , that converts a a class to a table name
 * @Id - makes the variable as primary key for the table
 * @Enumerated - tells that the enum will be stored in table | either by string or number by default
 */

@Getter
@Setter
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UID;
    private String name;
    private String authorName;
    private String publisherName;
    private String pages;
    @Enumerated(value = EnumType.STRING)
    private Cateogory bookCateogory;
    @Enumerated()
    private Language language;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
}
