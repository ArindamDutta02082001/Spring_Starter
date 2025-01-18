package com.springboot.service;

import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This is the service file we create where we write the data handling logics
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;



    // creates a new employee by taking the request form the POST call
    public Employee createEmployee(Employeedto request)
    {
        Employee newEmp = new Employee();
        newEmp.setEFName(request.getEFName());
        newEmp.setELName(request.getELName());
        newEmp.setMobile(request.getMobile());
        newEmp.setAddress(request.getAddress());
        newEmp.setDepartment(request.getDepartment());

        employeeRepository.saveEmployee(newEmp);

        return newEmp;
    }

    // to get a particular employee upon a GET call
    public Employee getEmployee(String empid)
    {
        return employeeRepository.getEmployeeFromDB(empid);
    }

    // to get the list of all employees upon a GET Call
    public List<Employee> getAllEmployee()
    {
        return employeeRepository.getAllEmployeeFromDB();
    }


    // to update an employee based on its id upon PUT call
    public Employee updateEmployee(UpdateEmployeedto request , String employeeID)
    {
        Employee oldEmp = employeeRepository.getEmployeeFromDB(employeeID);
        if(oldEmp == null)
        {
            System.out.print("no Employee exist");
        }
        else
        {
            // we use some other external library to merge the new fields into new old object

            // custom merge function written for this project only , dont do like this

            Employee patchEmployee = oldEmp;

            patchEmployee.setEFName(request.getEFName());
            patchEmployee.setELName(request.getELName());
            patchEmployee.setMobile(request.getMobile());
            patchEmployee.setAddress(request.getAddress());
            patchEmployee.setDepartment(request.getDepartment());

            patchEmployee.setUniqueID( employeeID );
            patchEmployee.setDateCreated( oldEmp.getDateCreated() );
            employeeRepository.removeEmployee(employeeID);
            employeeRepository.saveEmployee(patchEmployee);

            return patchEmployee;

        }
        return null;
    }

    // to delete an employee upon delete Call
    public String deleteEmployee(String employeeID)
    {
        if(employeeRepository.removeEmployee(employeeID))
            return "Employee :"+employeeID+" deleted";
        else
            return "not found";
    }

}
