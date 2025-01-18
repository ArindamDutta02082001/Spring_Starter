package com.springboot.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import com.springboot.model.Address;

/**
 * DTO - consists the model to accept the JSON body
 * This dto will accept the incoming http request body for the first POST mapping i.e,
 * to create a new Employee
 *
 */
@Getter
@Setter
public class Employeedto {


    @NotBlank(message = "FName is mandatory")
    private String EFName;

    @NotBlank(message = "LName is mandatory")
    private String ELName;

    @NotNull
    private Address Address;

    @NotBlank
    @Size(min=2, max=30)
    private String Mobile;

    @NotNull
    private String Department;


}
