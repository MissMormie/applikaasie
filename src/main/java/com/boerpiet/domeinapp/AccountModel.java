/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.account.AccountDAO;
import com.boerpiet.cheeseapp.account.AccountDAOFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Sonja
 */
public class AccountModel {
    private LoginManager loginManager;
    private final Logger logger = LoggerFactory.getLogger(AccountModel.class);

    public AccountModel() {
        
    }

    public AccountModel(LoginManager loginManager) {
        this.loginManager = loginManager;
    }

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
    
    // TODO make sure username is unique.
    public String createAccount(String username, String password, int klantId) {
        AccountDAO ad = AccountDAOFactory.getAccountDAO();
        try {
            if(ad.userExists(username))
                return "user exists";
        } catch (SQLException ex) {
            return "exception";
        }
        
        AccountPojo account = new AccountPojo(username, password);
        account.setKlantId(klantId);
        if (klantId == 0)
            account.setAccountStatus("medewerker");
        else 
            account.setAccountStatus("klant");
        if (ad.createAccount(account)) {
            logger.info("new account created id: " + account.getKlantId() + account.getGebruikersnaam());
            return "true";
        }
        return "exception";
    }
    
    /**
     * 
     * @param account AccountPojo
     * @return boolean - false if update failed, true on success
     */
    public boolean updateAccountById(AccountPojo account) {
        if(account.getIdAccount()== 0) 
            return false;
        return AccountDAOFactory.getAccountDAO().updateAccountById(account);
    }
    
    public ArrayList<AccountPojo> fetchAccountList() {
        return AccountDAOFactory.getAccountDAO().getAllAccounts();
    }

    public boolean deleteAccountById(int id) {
        return AccountDAOFactory.getAccountDAO().deleteAccountById(id);
    }

    public AccountPojo getAccountById(int id) {
        return AccountDAOFactory.getAccountDAO().getAccountById(id);        
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
