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

    /**
     * 
     * @param sql String
     * @return int 0 means no record was saved
     */
    public int createAndReturnID(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating adres failed, no rows affected.");
            }
       
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
         } catch (Exception ex) {
            Logger.getLogger(MySQLConnection.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
        
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
}
