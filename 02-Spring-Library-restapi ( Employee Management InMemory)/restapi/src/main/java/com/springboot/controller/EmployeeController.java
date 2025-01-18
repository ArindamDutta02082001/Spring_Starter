package com.springboot.controller;

import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import com.springboot.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/api/v1")
public class EmployeeController {


    @Autowired
    EmployeeService employeeService;

    // getting a individual employee
    @GetMapping("/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID)
    {
        System.out.println("EmployeeId is passed by Path Variable");
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
    public List<Employee> getAllEmployee()
    {
        return employeeService.getAllEmployee();
    }

    // to save a new employee
    @PostMapping("/regemployee")                                                // POST
    public Employee registerEmployee(@RequestBody Employeedto request)
    {
        System.out.println("json body");
        System.out.println(request);

        // the below functionality should be kept in service file
        Employee newEmployee = employeeService.createEmployee(request);

    return newEmployee;

    }

    // to update an existing employee
    @PutMapping("/updemployee/{eid}")
    public Employee updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID)
    {
        // the update logic is moved to service file
        return employeeService.updateEmployee(request, employeeID);
    }

    // to delete an employee
    @DeleteMapping("/delemployee")
    public String deleteEmployee(@RequestParam("eid") String employeeID)
    {
        // delete logic is moved to service file
        return employeeService.deleteEmployee(employeeID);
    }


}
