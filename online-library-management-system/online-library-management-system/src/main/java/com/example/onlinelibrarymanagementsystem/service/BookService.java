package com.example.onlinelibrarymanagementsystem.service;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.dto.CreateBookDto;
import com.example.onlinelibrarymanagementsystem.dto.CreateStudentDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.models.Book;
import com.example.onlinelibrarymanagementsystem.models.Student;
import com.example.onlinelibrarymanagementsystem.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;

    // creating a new Book Java Object
    private Book createBook(CreateBookDto bookDto)
    {
        return  Book.builder()
                .name(bookDto.getName())
                .genre(bookDto.getGenre())
                .pages(bookDto.getPages())
                .author(Author.builder()
                        .authorName(bookDto.getAuthorName())
                        .email(bookDto.getEmail())
                        .country(bookDto.getCountry())
                        .build())
                .build();
    }

    public Book saveBook(CreateBookDto bookDto)
    {
        Book newBook = createBook(bookDto);
        bookRepository.save(newBook);
        return newBook;
    }

    public List<Book> getAllBooks()
    {
        return bookRepository.findAll();
    }
    public Book getBookById( Integer bookId)
    {
        return bookRepository.findById(bookId).orElse(null);
    }

    public Book deleteBookId( Integer bookId)
    {
        Book book = bookRepository.findById(bookId).orElse(null);
        if(book != null)
        {
            bookRepository.deleteById(bookId);
        }
        return book;
    }

}
