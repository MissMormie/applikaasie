/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.account.AccountDAOFactory;
import java.util.ArrayList;


/**
 *
 * @author Sonja
 */
public class AccountModel {
    
    public boolean validateLogin(String usernamePassword) {
        String[] parts = usernamePassword.split(" ");
        if (parts.length < 2) {
            return false;
        }
        AccountPojo login = new AccountPojo(parts[0], parts[1]);
        return AccountDAOFactory.getAccountDAO("MySQL").fillAccountPojoByUsernamePassword(login);
    }
    
    public boolean createAccount(String username, String password, String accountStatus) {
        AccountPojo account = new AccountPojo(username, password);
        account.setAccountStatus(accountStatus);
        return AccountDAOFactory.getAccountDAO("MySQL").createAccount(account);
    }
    
    public ArrayList<AccountPojo> fetchAccountList() {
        return AccountDAOFactory.getAccountDAO("MySQL").getAllAccounts();
    }

    public boolean deleteAccountById(int id) {
        return AccountDAOFactory.getAccountDAO("MySQL").deleteAccountById(id);
    }

}
