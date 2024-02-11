package com.example.bookmanagement.services;

import com.example.bookmanagement.dto.Bookdto;
import com.example.bookmanagement.model.Book;
import org.springframework.stereotype.Service;

@Service
public class Bookservice {

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


}
