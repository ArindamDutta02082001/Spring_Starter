package com.example.onlinelibrarymanagementsystem.controller;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    // to create a new Author
    @PostMapping("/author")
    public Author createAndSaveAuthor(@RequestBody CreateAuthorDto createAuthorDto)
    {
        return authorService.saveAuthor(createAuthorDto);
    }

    @GetMapping("/allauthor")
    public List<Author> getAllAuthors()
    {
        return authorService.getAuthors();
    }
}
