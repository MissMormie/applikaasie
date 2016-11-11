/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;

/**
 *
 * @author Sonja
 * UsageExample: AccountDAO dao = AccountDAOFactory.getAccountDAO("MySQL");
 * 
 */
public class AccountDAOFactory {
    // Todo make this settable and generic.
    private static final String type = "MySQL";
    
    /**
     * @deprecated Use getAccountDAO() instead
     * @param type
     * @return 
     */
    public static AccountDAO getAccountDAO(String type){
            if (type.equals("MySQL")) {
                    return new MySQLAccountDAO();
            } else if (type.equals("Firebird")) {
                    return new FirebirdAccountDAO();
            } 
            return new MySQLAccountDAO();
    }   
    
    public static AccountDAO getAccountDAO(){
            if (type.equals("MySQL")) {
                    return new MySQLAccountDAO();
            } else if (type.equals("Firebird")) {
                    return new FirebirdAccountDAO();
            } 
            return new MySQLAccountDAO();
    }    
    
    
    
    
    
}
