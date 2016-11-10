/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;


import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.AccountPojo;

/**
 *
 * @author Sonja
 */
public abstract class AccountDAO {
    
    /**
     * 
     * @param model AccountPojo
     * @return Boolean true on success
     */
    public abstract boolean createAccount(AccountPojo model);
    
    public abstract boolean updateAccount(AccountPojo model);

    public abstract boolean deleteAccount(AccountPojo model);

    public abstract boolean isValidLogin(AccountPojo model);
    
    public abstract AccountPojo getAccountByID(int accountId);

    public abstract boolean getAccountByUsernamePassword(AccountPojo model);

}
