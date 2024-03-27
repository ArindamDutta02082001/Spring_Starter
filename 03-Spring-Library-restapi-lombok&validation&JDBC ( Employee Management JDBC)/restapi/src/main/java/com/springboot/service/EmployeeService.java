package com.springboot.service;

import com.springboot.dto.Employeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is the service file we create where we write the data handling logics
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // creates a new employee by taking the request form the POST endpoint function
    public Employee createEmployee(Employeedto request)
    {
        //not req by new , we create by @Builder in lombok
//        Employee newEmp = new Employee();
//        newEmp.setEFName(request.getEFName());
//        newEmp.setELName(request.getELName());
//        newEmp.setMobile(request.getMobile());
//        newEmp.setAddress(request.getAddress());
//        newEmp.setDepartment(request.getDepartment());

        // shortcut of creating object by new

        return Employee.builder()
                .ELName(request.getELName())
                .EFName(request.getEFName())
                .Mobile(request.getMobile())
                .Address(request.getAddress())
                .Department(request.getDepartment())
                .build();

    }




}
