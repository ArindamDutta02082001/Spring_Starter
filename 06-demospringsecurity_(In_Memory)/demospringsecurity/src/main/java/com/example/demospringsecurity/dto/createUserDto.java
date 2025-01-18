package com.example.demospringsecurity.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createUserDto {

    private String username;

    private String password;

    private String authorities;
}
