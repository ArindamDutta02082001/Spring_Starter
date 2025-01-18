package com.springboot.dto;

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

    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;


}
