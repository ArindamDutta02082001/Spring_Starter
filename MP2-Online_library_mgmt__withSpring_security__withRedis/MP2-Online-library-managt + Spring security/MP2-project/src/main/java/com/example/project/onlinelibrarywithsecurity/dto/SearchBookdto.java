package com.example.project.onlinelibrarywithsecurity.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

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
