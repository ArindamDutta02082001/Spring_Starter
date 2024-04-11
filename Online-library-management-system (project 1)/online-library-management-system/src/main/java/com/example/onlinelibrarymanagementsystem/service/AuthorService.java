package com.example.onlinelibrarymanagementsystem.service;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.repository.AuthorRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    AuthorRepositiry authorRepositiry;

    // creating a new Author Java Object
    private Author createAuthor(CreateAuthorDto authorDto)
    {
        return  Author.builder()
                .authorName(authorDto.getAuthorName())
                .email(authorDto.getEmail())
                .country(authorDto.getCountry())
                .build();
    }


    // to save a newly created Book upon a POST call
    public Author saveAuthor(CreateAuthorDto createAuthorDto)
    {
        Author author = createAuthor(createAuthorDto);
        authorRepositiry.save(author);
        return author;
    }


    // to get all the Author upon a GET call
    public List<Author> getAuthors()
    {
        return authorRepositiry.findAll();
    }

    // utility function -  to be used inside other functions
    // to get a author by his emailid
    public  Author getAuthorByEmail(String email)
    {
        return authorRepositiry.findAuthorByEmailfromDB(email);
    }


}
