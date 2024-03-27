package com.example.onlinelibrarymanagementsystem.dto;

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

    // since author_id is to be stored in the Book table as
    // author_id will act as a foreign key in the book table
    // and which is the primary key for the author table

    // so we have to take the author args from the payload
    // in the json request

    // it is only necessary if you want to put value in the foreign key in the
    // book table
    // if you dont do this , the value of the ofreign key will be null

    @NotBlank
    private String email;
    @NotNull
    private String authorName;

    @NotNull
    private String country;
}
