package com.springboot.model;

import com.springboot.repository.EmployeeRepository;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;


/**
 * Inside model folder we will have the employee model or schema
 * The employee model contains

 {
 "uniqueID": "e654effc-e2fc-4c85-9b73-16d989537952",
 "dateCreated": 1711825303824,
 "address": {
 "address": "Bhedia , West Bengal",
 "colony": "Near Huttala 7/8 block",
 "house": "2/5 SC block",
 "pin": 7131216
 },
 "elname": "Dutta",
 "efname": "Arindam Dutta",
 "department": "Computer Science",
 "mobile": "9620922432"
 }

 */

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Employee {

    Employee()
    {}

    private String uniqueID = UUID.randomUUID().toString();
    private Long dateCreated = System.currentTimeMillis();

    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;

}
