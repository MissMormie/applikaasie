/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao;


import java.sql.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Singleton die MySQL connectie opent en andere klassen in staat stelt hier gebruik van te maken.
 * Usage example:
 * ResultSet result = MySQLConnection.getMySQLConnection().getResult(String sql); 
 * Use MySQLConnection.getMySQLConnection().close() to end connection.
 * @author Sonja
 * 
 */
public class MySQLConnection {
    // ------------ VARIABLES ---------------------------------
    
    private String url = "jdbc:mysql://localhost/Applikaasie"; 
    private String username = "boerpiet";
    private String password = "kaaskop";
    private final static MySQLConnection mysqlConnection = new MySQLConnection();
    Connection connection;
    Statement statement;
    private final Logger logger = LoggerFactory.getLogger(MySQLConnection.class);


    // ------------ CONSTRUCTORS ---------------------------------

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
    
    // ------------ PUBLIC FUNCTIONS ---------------------------------
    
    /**
     * Return instance of the singleton MySQLConnection
     * @deprecated use connector class instead.
     * @return MySQLConnection the instance of {@link MySQLConnection}
     */
    public static MySQLConnection getMySQLConnection() {
        return mysqlConnection;
    }
    
    /**
     * Reads information from the database and return the result
     * 
     * @param sql the sql String
     * @return ResultSet with result of the sql statement.
     */
    public ResultSet read(String sql) {
        try {
            ResultSet result = statement.executeQuery(sql);
            return result;
        } catch (Exception ex) {
            logger.debug("sql query failed:" + sql + " " + ex);
            return null;
        }
    }
    
    /**
     * Executes Create update or delete sql statement
     * 
     * @param sql the sql String
     * @return boolean, true on success
     */
    public boolean createUpdateDelete(String sql) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {                     
            Class.forName("com.mysql.jdbc.Driver");
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
        } catch (Exception ex) {
            logger.debug("sql query failed:" + sql + " " + ex);
            return false;
        }
        return true;
    }
    
    /**
     * Executes sql create statement, returns id of first new row 
     * 
     * @param sql the sql String
     * @return int 0 means no record was saved
     */
    public int createAndReturnID(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);

            int affectedRows = statement.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("No rows affected.");
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
            logger.debug("sql query failed:" + sql + " " + ex);
            return 0;
        }
    }
    
    /**
     * Closes the database connection
     */    
    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.print(ex);
        }
    }
}