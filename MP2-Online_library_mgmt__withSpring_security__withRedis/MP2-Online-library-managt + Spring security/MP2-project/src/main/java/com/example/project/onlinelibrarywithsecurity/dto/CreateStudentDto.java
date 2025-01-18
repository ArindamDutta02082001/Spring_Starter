package com.example.project.onlinelibrarywithsecurity.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
@Component
public class CreateStudentDto {
    @NotNull
    private String name;

    @NotNull
    private String contact;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
