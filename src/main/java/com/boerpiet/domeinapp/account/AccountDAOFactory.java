/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp.account;

/**
 *
 * @author Sonja
 */
public class AccountDAOFactory {
    public static AccountDAO getAccountDAO(String type){
            if (type.equals("MySQL")) {
                    return new MySQLAccountDAO();
            } else if (type.equals("Firebird")) {
                    return new FirebirdAccountDAO();
            } 
            return new MySQLAccountDAO();
    }    
}
