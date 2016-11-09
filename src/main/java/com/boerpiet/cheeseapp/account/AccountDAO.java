/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;


import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.AccountModel;

/**
 *
 * @author Sonja
 */
public abstract class AccountDAO {
    
    /**
     * 
     * @param model AccountModel
     * @return Boolean true on success
     */
    public abstract boolean createAccount(AccountModel model);
    
    public abstract boolean updateAccount(AccountModel model);

    public abstract boolean deleteAccount(AccountModel model);

    public abstract boolean isValidLogin(AccountModel model);

    public abstract AccountModel getAccount(int accountId);

}
