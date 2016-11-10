/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;

import com.boerpiet.domeinapp.AccountModel;
import com.boerpiet.cheeseapp.MySQLConnection;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonja
 */
public class MySQLAccountDAO extends AccountDAO {
    @Override
    public boolean createAccount(AccountModel model) {
        String sql      = "INSERT INTO Artikel (Voornaam, Achternaam, Tussenvoegsel, Telefoonnummer, EmailAdres, deleted) VALUES ("
                        + "'" + model.getGebruikersnaam()   + "',"
                        + "'" + model.getWachtwoord()       + "',"
                        + "'" + model.getAccountStatus()    + "',"
                        + "'" + model.getDatum_aanmaak()    + "',"
                        + "'" + model.getKlantId()          + "',"
                        + "'" + model.isDeleted()           + "');";

        System.out.println(sql);
        createUpdateDeleteSQL(sql);
        return true;
    }    

    
    /**
     * 
     * @param model
     * @return true on success 
     * Based on the idAccount in the AccountModel the account in the database is updated.
     */
    @Override
    public boolean updateAccount(AccountModel model) {
        String sql      = "UPDATE Artikel SET "
                        + "Gebruikersnaam='" +  model.getGebruikersnaam()   + "',"
                        + "Wachtwoord='"     +  model.getWachtwoord()       + "',"
                        + "AccountStatus='"  +  model.getAccountStatus()    + "',"
                        + "Datum_Aanmaak='"  +  model.getDatum_aanmaak()    + "',"
                        + "KlantID='"        +  model.getKlantId()          + "',"
                        + "Deleted='"        +  model.isDeleted()           + "'"
                        + "WHERE idAccount=" +  model.getIdAccount()        + ";";
        try {
            createUpdateDeleteSQL(sql);
        } catch (Exception ex) {
            Logger.getLogger(MySQLAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;            
        }
        
        return true;

    }

    @Override
    public boolean deleteAccount(AccountModel model) {
        String sql = "DELETE FROM Artikel + WHERE idAccount=" + model.getIdAccount() + ";";
        
        return createUpdateDeleteSQL(sql);
    }

    @Override
    public boolean isValidLogin(AccountModel model) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AccountModel getAccount(int accountId) {
        String sql = "SELECT * FROM Artikel + WHERE idAccount=" + accountId + ";";
        
        readSQL(sql);
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private ResultSet readSQL(String sql) {
        ResultSet result = null;
        try {
            result = MySQLConnection.getMySQLConnection().read(sql);
        } catch (Exception ex) {
            Logger.getLogger(MySQLAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return result;
        }
        return result;
    }    
    
    private boolean createUpdateDeleteSQL(String sql) {
        try {
            MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        } catch (Exception ex) {
            Logger.getLogger(MySQLAccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;        
    }
}
