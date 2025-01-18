package com.example.project.onlinelibrarywithsecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
