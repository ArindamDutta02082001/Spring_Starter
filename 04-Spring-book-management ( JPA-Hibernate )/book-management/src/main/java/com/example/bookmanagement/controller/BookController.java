package com.example.bookmanagement.controller;

import com.example.bookmanagement.dto.Bookdto;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.enums.Cateogory;
import com.example.bookmanagement.model.enums.Language;
import com.example.bookmanagement.services.Bookservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// functionalities for book-management , in the service file
// createAndSaveNewBook - create a new book
// getAllBooks - get all books
// getBookById - get a book by a particular id
// getBookByLang - get a book by a particular language
// getBookByCateo - get a book by a particular cateogory
// deleteBookById - delete a book by id
// updateBook - update a book by id

/**
 * payload for new book
 * {
 *   "name": "Example Book",
 *   "authorName": "John Doe",
 *   "publisherName": "Publisher ABC",
 *   "pages": "600",
 *   "bookCateogory": "PHYSICS",
 *   "language": "HINDI"
 * }
 */

@RestController
public class BookController {

    @Autowired
    Bookservice bookservice;

    // to save a new Book
    @PostMapping("/book")
    public Book createNewBook(@RequestBody Bookdto request)
    {
        return bookservice.createAndSaveNewBook(request);
    }

    // to get all the Books
    @GetMapping("/book")
    public List<Book> getBooks()
    {
        return bookservice.getAllBooks();
    }

    // to get a book based on book id
    @GetMapping("/book/{bid}")
    public Book getBookById(@PathVariable("bid") int bookid)
    {
        return bookservice.getBookById(bookid);
    }


    // to find book by language
    @GetMapping("/getbookbylang")
    public List<Book> getBooksByLanguage(@RequestParam("lang") Language language)
    {
        // custom sql query
        return bookservice.getBookByLang(language);
    }


    @GetMapping("/getbookbycateo")
    public List<Book> getBooksByCateogory(@RequestParam("cateo") String cateogory)
    {
        return bookservice.getBookByCateo(cateogory);
    }

    @DeleteMapping("/book")
    public Book deleteBook(@RequestParam("bid") Integer bid)
    {
        return bookservice.deleteBookById(bid);
    }

    @PutMapping("/book")
    public Book updateBook (@RequestBody Bookdto request , @RequestParam("bid") Integer bookId)
    {
       return bookservice.updateNewBook(request, bookId);
    }

}
