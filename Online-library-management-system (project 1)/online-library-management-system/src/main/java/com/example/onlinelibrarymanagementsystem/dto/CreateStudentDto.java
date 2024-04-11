package com.example.onlinelibrarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class CreateStudentDto {
    @NotNull
    private String name;

    @NotNull
    private String contact;
}
