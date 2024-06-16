package com.demo.oauth2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class authenticateDto {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
