package com.example.onlinelibrarymanagementsystem.controller;

import com.example.onlinelibrarymanagementsystem.dto.CreateAuthorDto;
import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @PostMapping("/author")
    public Author createAndSaveAuthor(@RequestBody CreateAuthorDto createAuthorDto)
    {
        return authorService.saveAuthor(createAuthorDto);
    }
}
