package com.springboot.repository;

import com.springboot.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class EmployeeRepository {

    /**
     * the repository file contains the code of DB connection and data storage
     */


    private void createEmployeeTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "CREATE TABLE IF NOT EXISTS Employee(id varchar(40) , Fname varchar(20) , Lname varchar(20) , Department varchar(20) , Mobile varchar(40) , Address varchar(900) )";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }


    // to save a particular employee
    public void saveEmployee(Employee employee) throws SQLException {
        Connection connection = DBConnection.getConnection();
        String sql = "INSERT INTO Employee VALUES ('"+employee.getUniqueID()+"','"+employee.getEFName()+"','"+employee.getELName()+"','"+employee.getDepartment()+"','"+employee.getMobile()+"','"+employee.getAddress().toString()+"')";
        Statement statement = connection.createStatement();
        statement.execute(sql);
    }

}
