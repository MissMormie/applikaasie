/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao;

import com.boerpiet.utility.ConfigVars;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

/**
 *
 * @author Sonja
 */
public class Connector {
    /*
    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        if(dataSource == null)
            setUpConnectionPool();
        
        return dataSource.getConnection();
    }
    
    private static void setUpConnectionPool() {
        if(ConfigVars.getConnectionType().equals("JDBC"))
            setUpJDBC();
        else if (ConfigVars.getConnectionType().equals("c3po"))
            setUpC3po();
        else if (ConfigVars.getConnectionType().equals("HikariCP"))
            setUpHikariCP();
    }

    private static void setUpJDBC()  {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void setUpC3po() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
 */   
}
