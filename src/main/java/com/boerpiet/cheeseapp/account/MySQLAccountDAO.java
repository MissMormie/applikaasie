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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Sonja
 */
public class MySQLAccountDAO extends AccountDAO {
    
    /**
     * 
     * @param model
     * @return 
     */
    @Override
    public boolean createAccount(AccountPojo model) {
        if(model.getKlantId() == 0) {
            return createWorkerAccount(model);
        }
        return createCustomerAccount(model);     
    }
    
    private boolean createWorkerAccount(AccountPojo model) {
        String deleted = model.isDeleted() ? "1" : "0";

        String sql  = "INSERT INTO Account (Gebruikersnaam, Wachtwoord, AccountStatus, Datum_Aanmaak, deleted) VALUES ("
                    + "'" + model.getGebruikersnaam()                      + "',"
                    + "'" + model.getWachtwoordHash()                      + "',"
                    + "'" + model.getAccountStatus()                       + "',"
                    + "'" + createCalendarString(model.getDatum_aanmaak()) + "',"
                    + "'" + deleted                                        + "');";                
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }
    
    private String createCalendarString(GregorianCalendar cal) {
        int month = cal.get(Calendar.MONTH) +1;
        String datum = "" + cal.get(Calendar.YEAR) + "-"
                          + month + "-"
                          + cal.get(Calendar.DAY_OF_MONTH);
        return datum;
    }
    
    private boolean createCustomerAccount(AccountPojo model) {
        String deleted = model.isDeleted() ? "1" : "0";
        String sql  = "INSERT INTO Account (Gebruikersnaam, Wachtwoord, AccountStatus, Datum_Aanmaak, KlantID, deleted) VALUES ("
                    + "'" + model.getGebruikersnaam()                       + "',"
                    + "'" + model.getWachtwoordHash()                       + "',"
                    + "'" + model.getAccountStatus()                        + "',"
                    + "'" + createCalendarString(model.getDatum_aanmaak())  + "',"
                    + "'" + model.getKlantId()                              + "',"
                    + "'" + deleted                                         + "');";

        MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        return true;
    }     


    @Override
    public boolean updateAccountById(AccountPojo account) {
        if (account.getIdAccount() == 0)
            return false;
        
        String deleted = account.isDeleted() ? "1" : "0";
        String klantId = "";
        if(account.getKlantId() != 0)
            klantId =     " KlantId='"        +  account.getKlantId()          + "',";

        String sql  = "UPDATE Account SET "
                    + " Gebruikersnaam='" +  account.getGebruikersnaam()   + "',"
                    + " Wachtwoord='"     +  account.getWachtwoordHash()   + "',"
                    + " AccountStatus='"  +  account.getAccountStatus()    + "',"
                    + " Datum_Aanmaak='"  +  createCalendarString(account.getDatum_aanmaak()) + "',"
                    + klantId
                    + " Deleted='"        +  deleted                     + "'"
                    + " WHERE idAccount=" +  account.getIdAccount()        + ";";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
       
    }

    @Override
    public boolean deleteAccount(AccountPojo accountPojo) {
        return deleteAccountById(accountPojo.getIdAccount());
    }

    // TODO Decide if we need a specific class for valid login or if getAccountByUsernamePassword
    // is sufficient, if so, fix the method.
    @Override
    public boolean isValidLogin(AccountPojo accountPojo) {
        String sql = "SELECT * FROM Account WHERE "
                + "gebruikersnaam='" + accountPojo.getGebruikersnaam() + "' "
                + "AND wachtwoord='" + accountPojo.getWachtwoordHash() + "';";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        return true;
    }

    @Override
    public AccountPojo getAccountById(int accountId) {
        String sql = "SELECT * FROM Account WHERE idAccount=" + accountId + ";";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        AccountPojo ap = new AccountPojo(); 
        if(tryFillPojo(result, ap)) 
            return ap;
        else 
            return null;
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
        
        return tryFillPojo(result, accountPojo);
    }
    
    
    private boolean tryFillPojo(ResultSet result, AccountPojo accountPojo) {
        try {
            result.next();
            fillPojo(result, accountPojo);
            return true; 
        } catch (SQLException ex) { 
            return false;
        }
    }
    // changes a single column row from the account table into an accountPojo object
    // uses accountPojo as input parameter so you can also use it to update an
    // existing pojo.
    private void fillPojo(ResultSet result, AccountPojo accountPojo) throws SQLException {
        accountPojo.setIdAccount(result.getInt("idAccount"));
        accountPojo.setGebruikersnaam(result.getString("Gebruikersnaam"));
        accountPojo.setWachtwoordHash(result.getString("Wachtwoord"));
        accountPojo.setAccountStatus(result.getString("Accountstatus"));
        accountPojo.setKlantId(result.getInt("KlantId"));
        accountPojo.setDeleted(result.getBoolean("Deleted"));    
        accountPojo.setDatum_aanmaak(convertDateToGregorian(result.getDate("Datum_Aanmaak")));       
    }
    
    private GregorianCalendar convertDateToGregorian(Date date) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        return cal;
    }

    @Override
    public ArrayList<AccountPojo> getAllAccounts() {
        String sql =  "SELECT * FROM Account WHERE Deleted = 0";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        
        ArrayList<AccountPojo> list = new ArrayList<AccountPojo>();

        // Check if there was no result for this query, should never happen.
        // TODO what happens then? should this code even be here?
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
        String sql = "DELETE FROM Account WHERE idAccount = '" + id + "';";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }
}
