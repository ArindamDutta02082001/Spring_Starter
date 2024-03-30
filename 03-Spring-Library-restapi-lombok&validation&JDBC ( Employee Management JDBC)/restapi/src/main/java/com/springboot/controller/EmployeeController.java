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


    @GetMapping("/allemployee")
    public List<Employee> getAllEmployee() throws SQLException, JsonProcessingException {
        return employeeService.getAllEmployee();
    }

    @GetMapping("/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID) throws SQLException, JsonProcessingException {
        System.out.println("ok");
        return employeeService.getEmployee(employeeID);
    }


    @PostMapping("/regemployee")                                                // POST
    public Employee registerEmployee(@RequestBody Employeedto request) throws SQLException, JsonProcessingException {

        // the below functionality should be kept in service file

        return employeeService.createEmployee(request);

    }

    @PutMapping("/updemployee/{eid}")
    public String updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID) throws SQLException, JsonProcessingException {
        // the update logic is moved to service file
        return employeeService.updateEmployee(employeeID , request);
    }

    // here we are accepting the employeeid through query param
    // local
    @DeleteMapping("/delemployee")
    public String deleteEmployee(@RequestParam("eid") String employeeID) throws SQLException {
        // delete logic is moved to service file
        return employeeService.deleteEmployee(employeeID);
    }


}
