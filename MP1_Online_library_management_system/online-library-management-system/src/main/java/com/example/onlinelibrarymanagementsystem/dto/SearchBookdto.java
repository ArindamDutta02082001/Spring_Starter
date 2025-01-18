package com.example.onlinelibrarymanagementsystem.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class SearchBookdto {

    @NotBlank
    private String searchKey;

    @NotBlank
    private String searchValue;

    @NotBlank
    private String operator;


}
