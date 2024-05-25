package com.example.project.onlinelibrarywithsecurity.dto;


import com.example.project.onlinelibrarywithsecurity.models.libraryModels.Author;
import com.example.project.onlinelibrarywithsecurity.models.libraryModels.enums.Genre;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
public class CreateBookDto {

    @NotNull
    private String name;

    @NotNull
    private Genre genre;

    @NotNull
    private Integer pages;

    private Author author;

}
