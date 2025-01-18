package com.springboot.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.dto.Employeedto;
import com.springboot.dto.UpdateEmployeedto;
import com.springboot.model.Address;
import com.springboot.model.Employee;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class EmployeeRepository {

    /**
     * the repository file contains the code of DB connection and data storage
     */


    // to create a new Table of Employee  in the DB
    public static void createEmployeeTable() throws SQLException {
        Connection connection = DBConnection.getConnection();

        String sql = "CREATE TABLE IF NOT EXISTS Employee(id varchar(40) , datecreated varchar(255) , firstname varchar(20) , lastname varchar(20)  , address varchar(900) , mobile varchar(40), department varchar(20)  )";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }


    // to get the List of all Employees stored from the DB
    public List<Employee> getAllEmployeesFromDB() throws SQLException, JsonProcessingException {
        List<Employee> employees = new ArrayList<>();

        Connection connection = DBConnection.getConnection();
        Statement  statement = connection.createStatement();
        String sql = "SELECT * FROM Employee";
        ResultSet  resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                String uniqueID = resultSet.getString(1);
                Long date = resultSet.getLong(2);
                String firstName = resultSet.getString(3);
                String lastName = resultSet.getString(4);
                String addressJson = resultSet.getString(5);
                String mobile = resultSet.getString(6);
                String department = resultSet.getString(7);

                ObjectMapper objectMapper = new ObjectMapper();
                Address address = objectMapper.readValue(addressJson, Address.class);


                // Create Employee object with retrieved data
                Employee employee = new Employee(uniqueID,date ,firstName, lastName,address,mobile, department);
                employees.add(employee);
            }

        return employees;
    }

    public Employee getAEmployeeFromDB( String uniqueId) throws SQLException, JsonProcessingException {

        Connection connection = DBConnection.getConnection();
        Statement  statement = connection.createStatement();
        String sql = "SELECT * FROM Employee WHERE id = '"+uniqueId+"'";
        ResultSet  resultSet = statement.executeQuery(sql);

        if (resultSet.next()) {
            String uniqueID = resultSet.getString(1);
            Long date = resultSet.getLong(2);
            String firstName = resultSet.getString(3);
            String lastName = resultSet.getString(4);
            String addressJson = resultSet.getString(5);
            String mobile = resultSet.getString(6);
            String department = resultSet.getString(7);

            ObjectMapper objectMapper = new ObjectMapper();
            Address address = objectMapper.readValue(addressJson, Address.class);

            // Create Employee object with retrieved data
            return new Employee(uniqueID,date ,firstName, lastName,address,mobile, department);
        }

        return null;
    }


    // to save a particular employee into the DB

    public Employee saveEmployeeInDB(Employeedto request) throws SQLException, JsonProcessingException {
        // Generating a unique key
        String uniqueID = UUID.randomUUID().toString();
        // Generating current timestamp
        long dateCreated = System.currentTimeMillis();

        // Creating an Employee object
        Employee employee = new Employee(uniqueID, dateCreated, request.getEFName(), request.getELName(), request.getAddress(), request.getMobile(), request.getDepartment());

        Connection connection = null;
        PreparedStatement preparedStatement = null;


            connection = DBConnection.getConnection();
            ObjectMapper objectMapper = new ObjectMapper();
            String addressJson = objectMapper.writeValueAsString(employee.getAddress());

            String sql = "INSERT INTO Employee (id, dateCreated, firstname, lastname, address, mobile, department) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getUniqueID());
            preparedStatement.setLong(2, employee.getDateCreated());
            preparedStatement.setString(3, employee.getEFName());
            preparedStatement.setString(4, employee.getELName());
            preparedStatement.setString(5, addressJson);
            preparedStatement.setString(6, employee.getMobile());
            preparedStatement.setString(7, employee.getDepartment());

            int n = preparedStatement.executeUpdate();

            if (n > 0) {
                return employee;
            } else {
                return null;
            }

    }
    // to delete a particular employee from the DB
    public Boolean deleteEmployeeInDB( String uniqueID) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "DELETE FROM Employee WHERE id = '"+uniqueID+"'";
        Statement statement = connection.createStatement();
        int rowsDeleted = statement.executeUpdate(sql);

            return rowsDeleted > 0;
    }

    // to update a particular employee base on id
    public Boolean updateEmployeeInDB(String employeeID , UpdateEmployeedto request) throws SQLException, JsonProcessingException {
        Connection connection = DBConnection.getConnection();
        Employee patchEmployee = null;

        String findOldEmployee = "SELECT * FROM Employee WHERE id = '"+employeeID+"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(findOldEmployee);

        if(resultSet.next())
        {

            ObjectMapper objectMapper = new ObjectMapper();
            String addressJson = objectMapper.writeValueAsString(request.getAddress());


            String updateEmployee = "UPDATE Employee SET datecreated = ? , firstname = ? , lastname = ? , address = ? , mobile = ? , department = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateEmployee);
            preparedStatement.setString(1,resultSet.getString(2));
            preparedStatement.setString(2,request.getEFName());
            preparedStatement.setString(3,request.getELName());
            preparedStatement.setString(4,addressJson);
            preparedStatement.setString(5,request.getMobile());
            preparedStatement.setString(6,request.getDepartment());
            preparedStatement.setString(7,employeeID);

            int n = preparedStatement.executeUpdate();

            return n > 0;

        }

            return false;
    }


}
