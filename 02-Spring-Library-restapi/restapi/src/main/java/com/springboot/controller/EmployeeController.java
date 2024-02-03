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
 */
@RestController
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employee/{eid}")
    public Employee getEmployee(@PathVariable("eid") String employeeID)
    {
        return employeeRepository.getEmployee(employeeID);
    }

    @GetMapping("/allemployee")
    public List<Employee> getAllEmployee()
    {
        return employeeRepository.getAllEmployee();
    }

    @PostMapping("/regemployee")                                                // POST
    public Employee registerEmployee(@RequestBody Employeedto request)
    {
        System.out.println("json body");
        System.out.println(request);

        // the below functionality should be kept in service file
        // creating a new Employee

//        Employee newEmployee = new Employee();
//        newEmployee.setEFName(request.getEFName());
//        newEmployee.setELName(request.getELName());
//        newEmployee.setMobile(request.getMobile());
//        newEmployee.setAddress(request.getAddress());
//        newEmployee.setDepartment(request.getDepartment());


        Employee newEmployee = employeeService.createEmployee(request);
        employeeRepository.saveEmployee(newEmployee);

    return newEmployee;

    }

    @PutMapping("/updemployee/{eid}")
    public Employee updateEmployee (@RequestBody UpdateEmployeedto request , @PathVariable("eid") String employeeID)
    {
        // the update logic is moved to service file

//        Employee oldEmp = employeeRepository.getEmployee(employeeID);
//        if(oldEmp == null)
//        {
//            System.out.print("no Employee exist");
//        }
//        else
//        {
//            // we use some other external library to merge the new fields into new old object
//
//            // custom merge function written for this project only , dont do like this
//
//
//            Employee patchEmployee = oldEmp;
//
//            patchEmployee.setEFName(request.getEFName());
//            patchEmployee.setELName(request.getELName());
//            patchEmployee.setMobile(request.getMobile());
//            patchEmployee.setAddress(request.getAddress());
//            patchEmployee.setDepartment(request.getDepartment());
//
//            patchEmployee.setUniqueID( employeeID );
//            patchEmployee.setDateCreated( oldEmp.getDateCreated() );
//            employeeRepository.removeEmployee(employeeID);
//            employeeRepository.saveEmployee(patchEmployee);
//
//            return patchEmployee;
//
//        }
//        return null;

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
