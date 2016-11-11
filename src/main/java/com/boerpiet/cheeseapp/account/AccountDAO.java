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
    
    /**
     * 
     * @param accountPojo AccountPojo
     * @return true on success 
     * Based on the idAccount in the AccountPojo the account in the database is updated.
     * if klantId in model is 0, no klantId is given to the db.
     */
    public abstract boolean updateAccountById(AccountPojo accountPojo);

    public abstract boolean deleteAccount(AccountPojo model);

    public abstract boolean isValidLogin(AccountPojo model);
    
    // Returns null if no account with that id is found.
    public abstract AccountPojo getAccountById(int accountId);

    // TODO Change return type to AccountPojo? 
    public abstract boolean fillAccountPojoByUsernamePassword(AccountPojo model);

    public abstract ArrayList<AccountPojo> getAllAccounts();

    public abstract boolean deleteAccountById(int id);
}
