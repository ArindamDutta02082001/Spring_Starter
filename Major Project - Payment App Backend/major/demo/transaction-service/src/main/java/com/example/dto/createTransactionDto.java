package com.example.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class createTransactionDto {



    // for starting a transaction we need the receiver user and amount and purpose

    @NotBlank
    private String receiver; // phone number

    @NotNull
    private Double amount;

    private String purpose;

}
