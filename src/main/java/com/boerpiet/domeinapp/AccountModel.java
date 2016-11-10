/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.account.AccountDAOFactory;


/**
 *
 * @author Sonja
 */
public class AccountModel {
    
    
    public boolean validateLogin(String usernamePassword) {
        String[] parts = usernamePassword.split(" ");
        AccountPojo login = new AccountPojo(parts[0], parts[1]);
        return AccountDAOFactory.getAccountDAO("MySQL").getAccountByUsernamePassword(login);
    }

}
