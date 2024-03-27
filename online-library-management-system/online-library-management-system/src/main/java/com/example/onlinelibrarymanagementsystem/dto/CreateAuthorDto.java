package com.example.onlinelibrarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class CreateAuthorDto {

    @NotBlank
    private String email;
    @NotNull
    private String authorName;

    @NotNull
    private String country;
}
