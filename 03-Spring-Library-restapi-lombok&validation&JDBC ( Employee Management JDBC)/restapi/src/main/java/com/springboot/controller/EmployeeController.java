package com.springboot.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

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
 * @RequestMapping("/api/v1") - used just to add a common prefix over the endpoint url
 */
@RestController
@RequestMapping("api/v1")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    // getting a individual employee
    @GetMapping("/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID) throws SQLException, JsonProcessingException {
        System.out.println("ok");
        return employeeService.getEmployee(employeeID);
    }

    //    // here I am taking the employeeID by requestBody
//    @GetMapping("/employee")
//    public Employee getEmployeee(@RequestBody String employeeID)
//    {
//        System.out.println("EmployeeId is passed by Request Body");
//        return employeeService.getEmployee(employeeID);
//    }
//
//    //  here I am taking the employeeID by Query Parameter
//    @GetMapping("/employee")
//    public Employee getEmployeeee(@RequestParam("eid") String employeeID)
//    {
//        System.out.println("EmployeeId is passed by Request Body");
//        return employeeService.getEmployee(employeeID);
//    }

    // getting a list of employee
    @GetMapping("/allemployee")
    public List<Employee> getAllEmployee() throws SQLException, JsonProcessingException {
        return employeeService.getAllEmployee();
    }

    // to save a new employee
    @PostMapping("/regemployee")                                                // POST
    public Employee registerEmployee(@RequestBody Employeedto request) throws SQLException, JsonProcessingException {

        // the below functionality should be kept in service file

        return employeeService.createEmployee(request);

    }


    // to update an existing employee
    @PutMapping("/updemployee/{eid}")
    public String updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID) throws SQLException, JsonProcessingException {
        // the update logic is moved to service file
        return employeeService.updateEmployee(employeeID , request);
    }

    // to delete an employee
    @DeleteMapping("/delemployee")
    public String deleteEmployee(@RequestParam("eid") String employeeID) throws SQLException {
        // delete logic is moved to service file
        return employeeService.deleteEmployee(employeeID);
    }


}
