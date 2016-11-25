/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.account;

/**
 *
 * @author Sonja
 */
public class FirebirdAccountDAO extends AccountDAO {

    @Override
    protected String getCreateAccountSQL() {
        // I have no clue why but Firebird automatically changes the column name Datum_Aanmaak into DATUM_AANMAAK.
        return "INSERT INTO \"account\" "
             + "(\"Gebruikersnaam\", \"Wachtwoord\", \"Accountstatus\", "
             + "\"DATUM_AANMAAK\", \"KlantId\", \"Deleted\") "
             + "VALUES (?, ?, ?, ?, ?, ?);";
    }

    @Override
    protected String getUpdateAccountByIdSQL() {
        return "UPDATE \"account\" SET \"Gebruikersnaam\"=?,"
             + " \"Wachtwoord\"=?, \"Accountstatus\"=?, \"DATUM_AANMAAK\"=?, "
             + "\"KlantId\"=?, \"Deleted\"=? WHERE \"idAccount\"=?;";
    }

    @Override
    protected String getAccountByIdSQL() {
        return "SELECT * FROM \"account\" "
             + "WHERE \"idAccount\"=? AND \"Deleted\"='0';";
    }

    @Override
    protected String getFillAccountPojoByUsernamePasswordSQL() {
        return "SELECT * FROM \"account\" WHERE \"Gebruikersnaam\"=? "
                        + "AND \"Wachtwoord\"=? AND \"Deleted\"='0';";
    }

    @Override
    protected String getAllAccountsSQL() {
        return "SELECT * FROM \"account\" WHERE \"Deleted\"='0';";
    }

    @Override
    protected String getDeleteAccountByIdSQL() {
        return "UPDATE \"account\" SET \"Deleted\"='1' WHERE \"idAccount\"=?;";
    }

    @Override
    protected String getDeleteAccountsByKlantIdSQL() {
        return "Update \"account\" SET \"Deleted\" = '1' WHERE \"KlantID\" = ?";    
    }

    @Override
    protected String getUserExistsSQL() {
        return "SELECT * FROM \"account\" WHERE \"Deleted\"='0' AND \"Gebruikersnaam\" = ? ;";

    }
}
