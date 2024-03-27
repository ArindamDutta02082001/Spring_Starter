package com.springboot.repository;

import org.springframework.beans.factory.annotation.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    @Value("${db_url}")
    static String db_url;
    @Value("${db_username}")
    static String db_u;
    @Value("${db_password}")
    static String db_p;


    public static Connection getConnection () throws SQLException {
         Connection connection;
        connection = DriverManager.getConnection(db_url,db_u,db_p);
        return connection;
    }
}
