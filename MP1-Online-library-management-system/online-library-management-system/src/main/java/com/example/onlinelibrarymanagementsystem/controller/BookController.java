package com.example.onlinelibrarymanagementsystem.controller;

import com.example.onlinelibrarymanagementsystem.dto.CreateBookDto;
import com.example.onlinelibrarymanagementsystem.dto.SearchBookdto;
import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.service.AuthorService;
import com.example.onlinelibrarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    // To create a new book
    @PostMapping("/regbook")
    public Book createAndSaveBook(@RequestBody @Valid CreateBookDto createBookDto)
    {
        return bookService.saveBook(createBookDto);

    }

    // to get the list of all books
    @GetMapping("/allbook")
    public List<Book> getBooks()
    {
        return bookService.getAllBooks();
    }



    // to delete a book
    @DeleteMapping("/delbook")
    public Book deleteBookById(@RequestParam("bid") Integer bookId)
    {
        Book book = bookService.getBookById(bookId);
        bookService.deleteBookId(bookId);
        return book;
    }


    // to update a book by its id
    @PutMapping("/updatebook")
    public Book updateBookById( @RequestBody CreateBookDto request , @RequestParam("bid") Integer bookId)
    {
        return bookService.updateNewBook(request,bookId);
    }

    // to search a book by its id
    @GetMapping("/book/{bookid}")
    public Book getBookById(@PathVariable("bookid") Integer bookId)
    {
        return bookService.getBookById(bookId);
    }

    /* *********************************  implemented search functionality **************************** */

    // to search  a book based on author , title , genre

    @GetMapping("/search")
    public List<Book> getBooksByAuthor(@RequestBody @Valid SearchBookdto searchBookRequest) throws Exception {
        return bookService.search(searchBookRequest);
    }


}
