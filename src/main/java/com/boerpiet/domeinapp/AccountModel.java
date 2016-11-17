/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.account.AccountDAOFactory;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Sonja
 */
public class AccountModel {
    private LoginManager loginManager;
    private final Logger logger = LoggerFactory.getLogger(AccountModel.class);


    public LoginManager getLogin() {
        return loginManager;
    }
    
    // TODO niet als 1 string meegeven maar in controller splitsen.
    public boolean validateLogin(String usernamePassword) {
        String[] parts = usernamePassword.split(" ");
        if (parts.length < 2) {
            return false;
        }
        AccountPojo login = new AccountPojo(parts[0], parts[1]);
        if(AccountDAOFactory.getAccountDAO().fillAccountPojoByUsernamePassword(login)) { 
            loginManager = new LoginManager(login);
            logger.info("new login from user id:" + login.getKlantId() + " " + parts[0]);
            return true;
        }
        else
            return false;
    }
    
    // TODO create an create account with account status.
    public boolean createAccount(String username, String password, int klantId) {
        AccountPojo account = new AccountPojo(username, password);
        account.setKlantId(klantId);
        if (klantId == 0)
            account.setAccountStatus("medewerker");
        else 
            account.setAccountStatus("klant");
        if (AccountDAOFactory.getAccountDAO("MySQL").createAccount(account)) {
            logger.info("new account created id: " + account.getKlantId() + account.getGebruikersnaam());
            return true;
        }
        return false;
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

    public boolean updateAccountKlantId(AccountPojo account, int id) {
        account.setKlantId(id);
        return updateAccountById(account);
    }
}
