package com.example.onlinelibrarymanagementsystem.repository;

import com.example.onlinelibrarymanagementsystem.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book , Integer> {


    // save , findById , findAllById , deleteById bla bla are implemented

//    @Query()
//    public void createBook
}
