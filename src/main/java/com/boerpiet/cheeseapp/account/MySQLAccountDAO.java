/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;

import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.cheeseapp.MySQLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public class MySQLAccountDAO extends AccountDAO {
    @Override
    public boolean createAccount(AccountPojo model) {
        if(model.getKlantId() == 0) {
            return createWorkerAccount(model);
        }
        return createCustomerAccount(model);
         
    }
    
    private boolean createWorkerAccount(AccountPojo model) {
        
        String deleted = model.isDeleted() ? "1" : "0";
        String sql      = "INSERT INTO Account (Gebruikersnaam, Wachtwoord, AccountStatus, Datum_Aanmaak, deleted) VALUES ("
                        + "'" + model.getGebruikersnaam()   + "',"
                        + "'" + model.getWachtwoordHash()   + "',"
                        + "'" + model.getAccountStatus()    + "',"
                        + "'" + model.getDatum_aanmaak()    + "',"
                        + "'" + deleted                     + "');";

        System.out.println(sql);
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }    
    
    private boolean createCustomerAccount(AccountPojo model) {
        String sql      = "INSERT INTO Account (Gebruikersnaam, Wachtwoord, AccountStatus, Datum_Aanmaak, KlantID, deleted) VALUES ("
                        + "'" + model.getGebruikersnaam()   + "',"
                        + "'" + model.getWachtwoordHash()       + "',"
                        + "'" + model.getAccountStatus()    + "',"
                        + "'" + model.getDatum_aanmaak()    + "',"
                        + "'" + model.getKlantId()          + "',"
                        + "'" + model.isDeleted()           + "');";

        System.out.println(sql);
        MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        return true;
    }     

    /**
     * 
     * @param model
     * @return true on success 
 Based on the idAccount in the AccountPojo the account in the database is updated.
     */
    @Override
    public boolean updateAccount(AccountPojo model) {
        String sql      = "UPDATE Account SET "
                        + "Gebruikersnaam='" +  model.getGebruikersnaam()   + "',"
                        + "Wachtwoord='"     +  model.getWachtwoordHash()       + "',"
                        + "AccountStatus='"  +  model.getAccountStatus()    + "',"
                        + "Datum_Aanmaak='"  +  model.getDatum_aanmaak()    + "',"
                        + "KlantID='"        +  model.getKlantId()          + "',"
                        + "Deleted='"        +  model.isDeleted()           + "'"
                        + "WHERE idAccount=" +  model.getIdAccount()        + ";";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
       
    }

    @Override
    public boolean deleteAccount(AccountPojo accountPojo) {
        return deleteAccountById(accountPojo.getIdAccount());
    }

    // TODO Decide if we need a specific class for valid login or if getAccountByID
    // is sufficient, if so, fix the method.
    @Override
    public boolean isValidLogin(AccountPojo accountPojo) {
        String sql = "SELECT * FROM Account + WHERE "
                + "gebruikersnaam='" + accountPojo.getGebruikersnaam() + "' "
                + "AND wachtwoord='" + accountPojo.getWachtwoordHash() + "';";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        return true;
    }

    @Override
    public AccountPojo getAccountByID(int accountId) {
        String sql = "SELECT * FROM Account + WHERE idAccount=" + accountId + ";";
        
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    @Override
    public boolean fillAccountPojoByUsernamePassword(AccountPojo accountPojo) {        
        String sql =  "SELECT * FROM Account WHERE "
                    + "Gebruikersnaam='" + accountPojo.getGebruikersnaam() + "' "
                    + "AND Wachtwoord='" + accountPojo.getWachtwoordHash() + "';";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        
        // Check if there was a result for this account
        if (result == null) {
            System.out.print(result);
            return false;
        }
        
        try {
            result.next();
            fillPojo(result, accountPojo);
            return true; 
        } catch (SQLException ex) { 
            return false;
        }
    }
    
    private void fillPojo(ResultSet result, AccountPojo accountPojo) throws SQLException {
        accountPojo.setIdAccount(result.getInt("idAccount"));
        accountPojo.setGebruikersnaam(result.getString("Gebruikersnaam"));
        accountPojo.setWachtwoordHash(result.getString("Wachtwoord"));
        accountPojo.setAccountStatus(result.getString("Accountstatus"));
        accountPojo.setKlantId(result.getInt("KlantId"));
        accountPojo.setDeleted(result.getBoolean("Deleted"));
        accountPojo.setDatum_aanmaak(result.getDate("Datum_Aanmaak"));       
    }

    @Override
    public ArrayList<AccountPojo> getAllAccounts() {
        String sql =  "SELECT * FROM Account WHERE Deleted = 0";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        
        ArrayList<AccountPojo> list = new ArrayList<AccountPojo>();

        // Check if there was no result for this query, should never happen.
        if (result == null) {
            System.out.print(result);
        }
        
        try {
            while(result.next()) {
                AccountPojo ap = new AccountPojo(); 
                fillPojo(result, ap);
                list.add(ap);
            }
             
        } catch (SQLException ex) { 
            // TODO logger
            System.out.println("SQL Exception: " + ex);
        }
        return list;
    }

    @Override
    public boolean deleteAccountById(int id) {
        String sql = "DELETE FROM Account + WHERE idAccount=" + id + ";";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }
}
