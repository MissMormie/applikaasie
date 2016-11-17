/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

/**
 *
 * @author Sonja
 */
public class LoginManager {
    private AccountPojo account;
    
    public LoginManager(AccountPojo account) {
        this.account = account;
    }
    
    public void logout() {
        account = null;        
    }
    
    public boolean isLoggedIn() {
        return (account != null); 
    }
    
    public String getAccountStatus() {
        if(account != null) {
            return account.getAccountStatus();
        }
        return "";
    }
    
    @Override
    public String toString() {
        return account.toString();
    }
}

