package com.springboot.model;

import com.springboot.repository.EmployeeRepository;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Inside model folder we will have
 * the employee update model or schema
 * The employee model contains
 * {
 * 	   "efname": "Arindam" ,
 *     "elname": "Dutta" ,
 *     "address" :"Bhedia , West Bengal" ,
 *     "mobile" : 9620922432 ,
 *     "department" : "Computer Science"
 * }
 */

@Component
@Setter
@Getter
public class Employee {

    private String uniqueID = UUID.randomUUID().toString();
    private long dateCreated = System.currentTimeMillis();

    private long slNo = new EmployeeRepository().getTotalNoofEmployees();

    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;

}
