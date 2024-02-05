package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @GetMapping("/")
    public Book getBooks()
    {
        return null;
    }



}
