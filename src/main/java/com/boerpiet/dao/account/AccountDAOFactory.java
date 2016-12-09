/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.account;

import com.boerpiet.utility.ConfigVars;

/**
 *
 * @author Sonja
 * UsageExample: AccountDAO dao = AccountDAOFactory.getAccountDAO();
 * Takes info from config.xml to determine the database type.
 */
public class AccountDAOFactory {
    
    public static AccountDAO getAccountDAO(){
        String type = ConfigVars.getDbType();
        if (type.equals("MySQL")) {
                return new MySQLAccountDAO();
        } else if (type.equals("Firebird")) {
                return new FirebirdAccountDAO();
        }
        return new MySQLAccountDAO();
    }
    
}
