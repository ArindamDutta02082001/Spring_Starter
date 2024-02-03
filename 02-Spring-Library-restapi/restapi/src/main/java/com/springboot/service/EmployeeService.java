package com.springboot.service;

import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
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
        Employee newEmp = new Employee();
        newEmp.setEFName(request.getEFName());
        newEmp.setELName(request.getELName());
        newEmp.setMobile(request.getMobile());
        newEmp.setAddress(request.getAddress());
        newEmp.setDepartment(request.getDepartment());

        return newEmp;
    }

    public Employee updateEmployee(UpdateEmployeedto request , String employeeID)
    {
        Employee oldEmp = employeeRepository.getEmployee(employeeID);
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

    public String deleteEmployee(String employeeID)
    {
        if(employeeRepository.removeEmployee(employeeID) == true)
            return "Employee :"+employeeID+" deleted";
        else
            return "not found";
    }

}
