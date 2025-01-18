package com.example.bookmanagement.dto;

import com.example.bookmanagement.model.enums.Cateogory;
import com.example.bookmanagement.model.enums.Language;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

//payload :
//        {
//        "name": "Example Book",
//        "authorName": "John Doe",
//        "publisherName": "Publisher ABC",
//        "pages": "300",
//        "bookCateogory": "MATH",
//        "language": "ENGLISH"
//        }


@Getter
@Setter
public class Bookdto {

  @NotEmpty
    private String name;
  @NotBlank
    private String authorName;
  @NotEmpty
    private String publisherName;
  @NotNull
    private String pages;
  @NotEmpty
    private Cateogory bookCateogory;
  @NotEmpty
    private Language language;
}



