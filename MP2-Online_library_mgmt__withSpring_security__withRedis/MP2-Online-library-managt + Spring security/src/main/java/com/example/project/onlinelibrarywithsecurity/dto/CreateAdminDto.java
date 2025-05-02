package com.example.project.onlinelibrarywithsecurity.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminDto {
    @NotBlank
    private String name;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
