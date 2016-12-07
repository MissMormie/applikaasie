/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao;

import com.boerpiet.utility.ConfigVars;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 * TODO: Split this into 3 classes with a factory in between. 
 */
public class Connector {
                
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if(ConfigVars.getConnectionType().equals("jdbc"))
            return getJDBCConnection();

        if(dataSource == null)
            setUpConnectionPool();
        
        return dataSource.getConnection();
    }
    
    private static void setUpConnectionPool() {
        if (ConfigVars.getConnectionType().equals("c3po"))
            setUpC3p0();
        else if (ConfigVars.getConnectionType().equals("HikariCP"))
            setUpHikariCP();
    }

    private static Connection getJDBCConnection()  {
        Logger logger = LoggerFactory.getLogger(Connector.class);

            String url = ConfigVars.getDbLocation();
            String username = ConfigVars.getDbUsername();
            String password = ConfigVars.getDbPassword();

        try {
            Class.forName(ConfigVars.getDriver());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            logger.error("fatal exception in getting connection." + ex);
            System.exit(0);
            return null;
        }
    }

    private static void setUpC3p0() {
        
        Logger logger = LoggerFactory.getLogger(Connector.class);
        
        ComboPooledDataSource c3p0Ds = new ComboPooledDataSource ();
        
        try {
            c3p0Ds.setDriverClass(ConfigVars.getDriver());
            c3p0Ds.setJdbcUrl(ConfigVars.getDbLocation());
            c3p0Ds.setUser(ConfigVars.getDbUsername());
            c3p0Ds.setPassword(ConfigVars.getDbPassword());
            
            c3p0Ds.setMinPoolSize(1);
            c3p0Ds.setMaxPoolSize(15);
            c3p0Ds.setAcquireIncrement(2);
            
            c3p0Ds.setMaxStatements(250);
            c3p0Ds.setMaxStatementsPerConnection(2050);
            
            //number of attempting reconnection and delay in between retries by c3p0
            //in case of (temporary)database outage
            c3p0Ds.setAcquireRetryAttempts(5);
            c3p0Ds.setAcquireRetryDelay(120); //2 minutes?
            //c3p0 will not try to acquire connections again after the set number of retry-attempts
            c3p0Ds.setBreakAfterAcquireFailure(true);
            
            //1800 equals 30 minutes
            c3p0Ds.setMaxIdleTimeExcessConnections(900); // 15 minutes
            //or: c3p0Ds.setMaxConnectionAge(7200); //2 hours
                        
        } catch (PropertyVetoException ex) {
            //A PropertyVetoException is thrown when a proposed change
            //to a property represents an unacceptable value.
            logger.error("Property change has an unacceptable value: " +ex);
            setUpConnectionPool ();
        }
        dataSource = c3p0Ds; 
    }

    private static void setUpHikariCP() {
            HikariConfig config = new HikariConfig();
            
            config.setJdbcUrl(ConfigVars.getDbLocation());
            config.setUsername(ConfigVars.getDbUsername());
            config.setPassword(ConfigVars.getDbPassword());

            config.setMaximumPoolSize(10);
            config.setAutoCommit(true);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

            dataSource = new HikariDataSource(config);
    }
    
}
