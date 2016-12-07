/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.account.AccountDAO;
import com.boerpiet.dao.account.AccountDAOFactory;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.JDBCException;
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
    
    public boolean validateLogin(String username, String password) {
        AccountDAO ad = AccountDAOFactory.getAccountDAO();
        password = makeWachtwoordHash(password);
        AccountPojo login = ad.getAccountByUsernamePassword(username, password);
        if(login == null) 
            return false;

        loginManager = new LoginManager(login);
        logger.info("new login from user id:" + login.getKlant() + " " + username);
        return true;
    }
    
    public String createAccount(String username, String password, int klantId) {
        AccountDAO ad = AccountDAOFactory.getAccountDAO();
        try {
            if(ad.userExists(username))
                return "user exists";
        } catch (JDBCException ex) {
            return "exception";
        }
        
        password = makeWachtwoordHash(password);
        AccountPojo account = new AccountPojo(username, password);
        account.setKlant(klantId);
        if (klantId == 0)
            account.setAccountStatus("medewerker");
        else 
            account.setAccountStatus("klant");
        if (ad.createAccount(account)) {
            logger.info("new account created id: " + account.getKlant() + account.getGebruikersnaam());
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
        return AccountDAOFactory.getAccountDAO().updateAccount(account);
    }
    
    public List<AccountPojo> fetchAccountList() {
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

    public boolean updatePassword(AccountPojo account, String password) {
        password = makeWachtwoordHash(password);
        account.setWachtwoord(password);
        return updateAccountById(account);
    }

    public boolean updateAccountStatus(AccountPojo account, String in) {
        account.setAccountStatus(in);
        return updateAccountById(account);
    }

    public boolean updateAccountKlantId(AccountPojo account, int id) {
        account.setKlant(id);
        return updateAccountById(account);
    }
    
    /** 
     * Hash function from: http://stackoverflow.com/questions/3103652/hash-string-via-sha-256-in-java
     * 
     * @param base
     * @return 
     */
    private String makeWachtwoordHash(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException ex){
           throw new RuntimeException(ex);
        }
    }    
    
}
