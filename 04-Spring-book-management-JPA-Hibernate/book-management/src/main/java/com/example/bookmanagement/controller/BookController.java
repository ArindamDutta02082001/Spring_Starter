package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.Bookdto;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.Language;
import com.example.bookmanagement.repository.Bookrepository;
import com.example.bookmanagement.services.Bookservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    Bookservice bookservice;

    @Autowired
    Bookrepository bookrepository;

    @GetMapping("/allbooks")
    public List<Book> getBooks()
    {
        return bookrepository.findAll();
    }

    @GetMapping("/getbook")
    public List<Book> getBooks(@RequestParam("bid") int bookid)
    {
        return bookrepository.findAllById(Collections.singleton(bookid));
    }

    @PostMapping("/regbook")
    public Book createBook(@RequestBody Bookdto request)
    {
       Book newBook = bookservice.createBook(request);
       bookrepository.save(newBook);
       return newBook;

    }

    // custom sql query
    // to find book by language
    @GetMapping("/getbookbylang")
    public List<Book> getBooksByLanguage(@RequestParam("lang") Language language)
    {
        return bookrepository.findBookByLanguage(language);
    }





}
