package com.springboot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.ToString;

/**
 * Adress with multiple field
 */
@ToString
public class Address {
    @NotNull
    public String address;
    @NotNull
    public String colony;
    @NotNull
    public String house;
    @NotBlank
    public Integer pin;
}
