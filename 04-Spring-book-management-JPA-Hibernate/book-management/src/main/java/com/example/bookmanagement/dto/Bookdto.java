package com.example.bookmanagement.dto;

import com.example.bookmanagement.model.Cateogory;
import com.example.bookmanagement.model.Language;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import java.util.Date;

@Getter
@Setter
@Component

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
