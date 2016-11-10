/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp;


import java.sql.*;

/**
 * Singleton die MySQL connectie opent en andere klassen in staat stelt hier gebruik van te maken.
 * Usage example:
 * ResultSet result = MySQLConnection.getMySQLConnection().getResult(String sql); 
 * Use MySQLConnection.getMySQLConnection().close() to end connection.
 * @author Sonja
 */
public class MySQLConnection {
    private String url = "jdbc:mysql://localhost/Applikaasie";
    private String username = "boerpiet";
    private String password = "kaaskop";
    private final static MySQLConnection mysqlConnection = new MySQLConnection();
    Connection connection;
    Statement statement;

    
    private MySQLConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        }  catch (SQLException ex) {
            System.out.print(ex);
            System.exit(0);
        } catch (ClassNotFoundException ex) {
            System.out.print(ex);
        }
    }
    
    public static MySQLConnection getMySQLConnection() {
        return mysqlConnection;
    }
    
    public ResultSet read(String sql) throws Exception {
        ResultSet resultSet = statement.executeQuery(sql);
        return resultSet;
    }
    
    public void createUpdateDelete(String sql) throws Exception {
        statement.executeUpdate(sql);
    }
        
    public void close() {
        try {   
            connection.close();
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
}
