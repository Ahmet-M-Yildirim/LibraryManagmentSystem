package com.example.lms.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseHandler {
    private static final String DB_URL = "jdbc:mysql://database-1.cbm4geausgxr.eu-north-1.rds.amazonaws.com:3306/librarymanagmentsystem";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "K2wbWX105Eg8kJl0g9ER";
    private Connection conn;

    private static DataBaseHandler handler = null;

    public DataBaseHandler() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database Connected");
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
        }
    }

    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
    }

    public Connection getConnection() {
        return conn;
    }


    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing the database connection: " + e.getMessage());
        }
    }


}