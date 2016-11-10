/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp;

import com.boerpiet.cheeseapp.account.MySQLAccountDAO;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public ResultSet read(String sql) {
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
        } catch (Exception ex) {
            Logger.getLogger(MySQLAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }
        return result;        
    }
    
    public boolean createUpdateDelete(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;             
    }
        
    public void close() {
        try {   
            connection.close();
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
}
