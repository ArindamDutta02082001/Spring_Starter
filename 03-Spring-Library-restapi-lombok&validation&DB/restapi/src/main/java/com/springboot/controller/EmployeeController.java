package com.springboot.controller;

import com.springboot.dto.Employeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

/**
 *
 * Inside the Employee controller we will have our
 * rest end points for CRUD operation
 * GET ,
 * POST ,
 * PUT ,
 * PATCH ,
 * DELETE
 *
 */

/**
 * @Restcontroller - used to tell that the function contains rest endpoints
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @PostMapping("/regemployee")
    public Employee registerEmployee(@RequestBody @Valid Employeedto request) throws SQLException {

        Employee newEmployee = employeeService.createEmployee(request);
        employeeRepository.saveEmployee(newEmployee);

    return newEmployee;

    }



}
