/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;


import com.boerpiet.domeinapp.AccountPojo;
import java.util.ArrayList;

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

    // TODO Change return type to AccountPojo
    public abstract boolean fillAccountPojoByUsernamePassword(AccountPojo model);

    public abstract ArrayList<AccountPojo> getAllAccounts();

    public abstract boolean deleteAccountById(int id);
}
