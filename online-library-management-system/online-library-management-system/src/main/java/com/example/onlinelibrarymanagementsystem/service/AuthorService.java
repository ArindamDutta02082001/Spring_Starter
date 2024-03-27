package com.example.onlinelibrarymanagementsystem.service;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.repository.AuthorRepositiry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Author saveAuthor(CreateAuthorDto createAuthorDto)
    {
        Author author = createAuthor(createAuthorDto);
        authorRepositiry.save(author);
        return author;
    }
}
