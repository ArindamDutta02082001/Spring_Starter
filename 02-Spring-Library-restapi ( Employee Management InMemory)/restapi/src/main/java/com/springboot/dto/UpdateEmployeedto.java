package com.springboot.dto;
import com.springboot.model.Address;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

/**
 * DTO - consists the model to accept the JSON body
 * This dto will acceot the incoming http request body for the second POST mapping i.e,
 * to update a new Employee
 */

@Component
@Getter
@Setter
public class UpdateEmployeedto {

    private String EFName;

    private String ELName;

    private Address Address;

    private String Mobile;

    private String Department;

}
