package com.example.bookmanagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 * @Entity - is a JPA annotation , that converts a java class to a table name
 *              The table name gets automatically converted
 *              Book -> book
 *              PublisherName -> publisher_name
 * @Id - makes the variable as primary key for the table
 * @Enumerated - tells that the enum will be stored in table | either by string or number by default
 * @AllArgsConstructor - used to eliminate some error , to do
 * @Column - optional , if you want to rename a column or put some condition while creating the table
 */

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UID;
    private String name;
    @Column(name="Author Name", nullable=false, length=512)
    private String authorName;
    private String publisherName;
    private String pages;
    @Enumerated(value = EnumType.ORDINAL)
    private Cateogory bookCateogory;
    @Enumerated()
    private Language language;
    @CreationTimestamp
    private Date createdOn;
    @UpdateTimestamp
    private Date updatedOn;
   public Book()
    {}
}
