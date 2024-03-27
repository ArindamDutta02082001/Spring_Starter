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
 * @RequestMapping("/home") - used just to add a common prefix over the endpoint url
 */

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;


    @GetMapping("/allemployee")
    public List<Employee> getAllEmployee()
    {
        return employeeRepository.getAllEmployee();
    }

    @GetMapping("/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID)
    {
        System.out.println("ok");
        return employeeRepository.getEmployee(employeeID);
    }


    @PostMapping("/regemployee")                                                // POST
    public Employee registerEmployee(@RequestBody Employeedto request)
    {
        System.out.println("json body");
        System.out.println(request);

        // the below functionality should be kept in service file
        Employee newEmployee = employeeService.createEmployee(request);
        employeeRepository.saveEmployee(newEmployee);

    return newEmployee;

    }

    @PutMapping("/updemployee/{eid}")
    public Employee updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID)
    {
        // the update logic is moved to service file
        return employeeService.updateEmployee(request, employeeID);
    }

    // here we are accepting the employeeid through query param
    // local
    @DeleteMapping("/delemployee")
    public String deleteEmployee(@RequestParam("eid") String employeeID)
    {
        // delete logic is moved to service file
//        if(employeeRepository.removeEmployee(employeeID) == true)
//            return "Employee :"+employeeID+" deleted";
//        else
//            return "not found";

        return employeeService.deleteEmployee(employeeID);
    }


}
