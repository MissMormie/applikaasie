/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant;


/**
 *
 * @author Sonja
 */
public class FirebirdKlantDAO extends KlantDAO {

    @Override
    protected String getCreateKlantSQL() {
        return "INSERT INTO \"klant\" (\"Voornaam\", \"Achternaam\", \"Tussenvoegsel\", "
                    + "\"Telefoonnummer\", \"Emailadres\", \"Deleted\") "
                    + "VALUES (?, ?, ?, ?, ?, '0')";
    }

    @Override
    protected String getAllKlantenSQL() {
        return "SELECT * FROM \"klant\" WHERE \"Deleted\"='0';";
    }
   
    @Override
    protected String getUpdateKlantByIdSQL() {
        return "Update \"klant\" SET \"Voornaam\" = ?, \"Achternaam\" = ?, "
            + "\"Tussenvoegsel\" = ?, \"Telefoonnummer\" = ?, \"Emailadres\" = ?, "
            + "\"Deleted\" = ? WHERE \"idKlant\" = ?;";
    } 
      
    @Override
    protected String getKlantPojoByIdSQL() {
        return "SELECT * FROM \"klant\" WHERE \"idKlant\" = ? AND \"Deleted\"='0';";
    }

    @Override
    protected String getDeleteKlantByIdSQL() {
        return "Update \"klant\" SET \"Deleted\" = 1 WHERE \"KlantID\" = ?";
    }
       
}
