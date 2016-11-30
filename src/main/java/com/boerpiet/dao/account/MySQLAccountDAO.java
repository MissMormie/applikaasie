/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.account;

/**
 *
 * @author Sonja
 */
public class MySQLAccountDAO extends AccountDAO {

    @Override
    protected String getCreateAccountSQL() {
        return "INSERT INTO Account "
                + "(Gebruikersnaam, Wachtwoord, AccountStatus, Datum_Aanmaak, KlantID, deleted) "
                + "VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateAccountByIdSQL() {
        return "UPDATE Account SET "
             + " Gebruikersnaam = ?, Wachtwoord = ?, AccountStatus = ?,"
             + " Datum_Aanmaak = ?, KlantId = ?, Deleted = ? "
             + " WHERE idAccount = ?;";    
    }

    @Override
    protected String getAccountByIdSQL() {
        return "SELECT * FROM Account WHERE idAccount = ? AND Deleted = 0;";
    }

    @Override
    protected String getFillAccountPojoByUsernamePasswordSQL() {
        return "SELECT * FROM Account WHERE "
             + "Gebruikersnaam = ? AND Wachtwoord = ? AND Deleted='0';";    
    }

    @Override
    protected String getAllAccountsSQL() {
        return "SELECT * FROM Account WHERE Deleted = 0";
    }

    @Override
    protected String getDeleteAccountByIdSQL() {
        return "DELETE FROM Account WHERE idAccount = ?;";
    }

    @Override
    protected String getDeleteAccountsByKlantIdSQL() {
        return "Update Account SET Deleted = 1 WHERE KlantID = ?";
    }

    @Override
    protected String getUserExistsSQL() {
        return "SELECT * FROM Account WHERE DELETED = 0 AND username = ?;";    }
    }
