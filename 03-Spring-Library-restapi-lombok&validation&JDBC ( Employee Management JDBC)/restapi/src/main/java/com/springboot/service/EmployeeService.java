package com.springboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
import com.springboot.model.Employee;
import com.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

/**
 * This is the service file we create where we write the data handling logics
 */
@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    // creates a new employee by taking the request form the POST endpoint function
    public Employee createEmployee(Employeedto request) throws SQLException, JsonProcessingException {

        return employeeRepository.saveEmployeeInDB(request);


    }



    // to get a particular employee upon a GET call
    public Employee getEmployee(String empid) throws SQLException, JsonProcessingException {
        return employeeRepository.getAEmployeeFromDB(empid);
    }

    // to get the list of all employees upon a GET Call
    public List<Employee> getAllEmployee() throws SQLException, JsonProcessingException {
        return employeeRepository.getAllEmployeesFromDB();
    }


    // to update an employee based on its id upon PUT call
    public String updateEmployee(String employeeID , UpdateEmployeedto request ) throws SQLException, JsonProcessingException {
        Boolean bool = employeeRepository.updateEmployeeInDB(employeeID , request);
        if( bool )
            return employeeID+" Updated ...";
        else
            return employeeID+" Updation failed";
    }

    // to delete an employee upon delete Call
    public String deleteEmployee(String employeeID) throws SQLException {
        if(employeeRepository.deleteEmployeeInDB(employeeID))
            return "Employee :"+employeeID+" deleted";
        else
            return "not found";
    }



}
