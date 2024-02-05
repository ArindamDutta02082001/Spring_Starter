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
    @Value("${db_url}") String db_url;
    @Value("${db_username}") String db_u;
    @Value("${db_password}") String db_p;

    private Connection connection;

    @Bean
    public void connectiontoDB () throws SQLException {
        this.connection = DriverManager.getConnection(db_url,db_u,db_p);
        createEmployeeTable();
    }

    private void createEmployeeTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Employee(id varchar(40) , Fname varchar(20) , Lname varchar(20) , Department varchar(20) , Mobile varchar(40) , Address varchar(900) )";
        Statement statement = this.connection.createStatement();
        statement.execute(sql);
    }


    // to save a particular employee
    public void saveEmployee(Employee employee) throws SQLException {
        String sql = "INSERT INTO Employee VALUES ('"+employee.getUniqueID()+"','"+employee.getEFName()+"','"+employee.getELName()+"','"+employee.getDepartment()+"','"+employee.getMobile()+"','"+employee.getAddress().toString()+"')";
        Statement statement = this.connection.createStatement();
        statement.execute(sql);
    }

}
