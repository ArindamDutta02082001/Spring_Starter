package com.example.onlinelibrarymanagementsystem.controller;

import com.example.onlinelibrarymanagementsystem.dto.CreateBookDto;
import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.Student;
import com.example.onlinelibrarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("/regbook")
    public Book createAndSaveBook(@RequestBody @Valid CreateBookDto createBookDto)
    {
        return bookService.saveBook(createBookDto);
    }

    @GetMapping("/books")
    public List<Book> getBooks()
    {
        return bookService.getAllBooks();
    }


    @GetMapping("/book/{bookid}")
    public Book getBookById(@PathVariable("bookid") Integer bookId)
    {
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/book")
    public Book deleteBookById(@RequestParam("bid") Integer bookId)
    {
        return bookService.deleteBookId(bookId);
    }

    @PutMapping("/book")
    public Book updateBookById( @RequestParam("bid") Integer bookId)
    {
        // todo
        return null;
    }
}
