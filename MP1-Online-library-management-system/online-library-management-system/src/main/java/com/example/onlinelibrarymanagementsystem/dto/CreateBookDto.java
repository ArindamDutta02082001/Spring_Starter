package com.example.onlinelibrarymanagementsystem.dto;

import com.example.onlinelibrarymanagementsystem.models.Author;
import com.example.onlinelibrarymanagementsystem.models.enums.Genre;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

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
