package com.example.onlinelibrarymanagementsystem.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthorDto {

    @NotBlank
    private String email;

    @NotNull
    private String authorName;

    @NotNull
    private String country;
}
