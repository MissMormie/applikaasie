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
    
    // TODO create an create account with account status.
    public boolean createAccount(String username, String password, int klantId) {
        AccountPojo account = new AccountPojo(username, password);
        account.setKlantId(klantId);
        if (klantId == 0)
            account.setAccountStatus("medewerker");
        else 
            account.setAccountStatus("klant");
        return AccountDAOFactory.getAccountDAO("MySQL").createAccount(account);
    }
    
    /**
     * 
     * @param account AccountPojo
     * @return boolean - false if update failed, true on success
     */
    public boolean updateAccountById(AccountPojo account) {
        if(account.getIdAccount()== 0) 
            return false;
        return AccountDAOFactory.getAccountDAO("MySQL").updateAccountById(account);
    }
    
    public ArrayList<AccountPojo> fetchAccountList() {
        return AccountDAOFactory.getAccountDAO("MySQL").getAllAccounts();
    }

    public boolean deleteAccountById(int id) {
        return AccountDAOFactory.getAccountDAO("MySQL").deleteAccountById(id);
    }

    public AccountPojo getAccountById(int id) {
        return AccountDAOFactory.getAccountDAO("MySQL").getAccountById(id);        
    }

    public boolean updateUsername(AccountPojo account, String username) {
        account.setGebruikersnaam(username);
        return updateAccountById(account);
    }

    public boolean updatePassword(AccountPojo account, String in) {
        account.setWachtwoordPlainText(in);
        return updateAccountById(account);
    }

    public boolean updateAccountStatus(AccountPojo account, String in) {
        account.setAccountStatus(in);
        return updateAccountById(account);
    }

    public boolean updateKlantId(AccountPojo account, int in) {
        account.setKlantId(in);
        return updateAccountById(account);
    }

}
