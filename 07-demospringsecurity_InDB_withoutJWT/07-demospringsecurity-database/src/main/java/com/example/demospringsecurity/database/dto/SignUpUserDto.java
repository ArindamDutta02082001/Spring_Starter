package com.example.demospringsecurity.database.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpUserDto {

    private String username;

    private String password;

    private String authorities;
}