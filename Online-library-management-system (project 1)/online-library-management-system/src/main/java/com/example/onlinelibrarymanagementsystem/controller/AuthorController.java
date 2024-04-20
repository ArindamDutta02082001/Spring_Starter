package com.example.onlinelibrarymanagementsystem.controller;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    AuthorService authorService;

    // to create a new Author
    @PostMapping("/regauthor")
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
