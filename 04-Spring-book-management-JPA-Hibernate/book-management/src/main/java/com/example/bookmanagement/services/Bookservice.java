package com.example.bookmanagement.services;

import com.example.bookmanagement.dto.Bookdto;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.model.enums.Cateogory;
import com.example.bookmanagement.model.enums.Language;
import com.example.bookmanagement.repository.Bookrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class Bookservice {

    @Autowired
    Bookrepository bookrepository;

    // creating a new Book Java Object
    public Book createBook(Bookdto request)
    {
            return Book.builder()
                    .name(request.getName())
                    .authorName(request.getAuthorName())
                    .publisherName(request.getPublisherName())
                    .pages(request.getPages())
                    .bookCateogory(request.getBookCateogory())
                    .language(request.getLanguage())
                    .build();
    }

    // functionalities for book-management
    // createAndSaveNewBook - create a new book
    // getAllBooks - get all books
    // getBookById - get a book by a particular id
    // getBookByLang - get a book by a particular language
    // getBookByCateo - get a book by a particular cateogory
    // deleteBookById - delete a book by id
    // updateBook - update a book by id

    public Book createAndSaveNewBook( Bookdto bookdto)
    {
        Book newBook = createBook(bookdto);
        bookrepository.save(newBook);
        return newBook;
    }

    public List<Book> getAllBooks()
    {
        return bookrepository.findAll();
    }

    public Book getBookById(int bid)
    {
        return bookrepository.findById(bid).orElse(null);
    }

    public  List<Book> getBookByLang(Language language)
    {
        // custom sql query
        return bookrepository.findBookByLanguage(language);
    }
    public  List<Book> getBookByCateo(String cateogory)
    {
        // custom sql query
        return bookrepository.findBookByCateo(cateogory);
    }

    public Book deleteBookById(Integer bid)
    {
        Book book = getBookById(bid);
        if( book != null)
            bookrepository.deleteById(bid);

        return book;
    }

    public Book updateNewBook( Bookdto request , Integer bookId )
    {
        Book book = createBook(request);
        book.setUID(bookId);
        bookrepository.updateBook(request.getName() , request.getAuthorName() ,request.getPublisherName() ,request.getPages(), request.getBookCateogory(), request.getLanguage() , bookId);
        return book;
    }

}
