/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.klant;


/**
 *
 * @author Sonja
 */
public class MySQLKlantDAO extends KlantDAO {
    
    @Override
    protected String getCreateKlantSQL() {
        return "INSERT INTO Klant (Voornaam, Achternaam, Tussenvoegsel, "
                    + "Telefoonnummer, Emailadres, Deleted) "
                    + "VALUES (?,?,?,?,?,'0')";
    }
    
    @Override
    protected String getAllKlantenSQL() {
        return "SELECT * FROM Klant WHERE Deleted = 0 ORDER BY Voornaam";
    }
      
    @Override
    protected String getUpdateKlantByIdSQL() {
        return "Update Klant SET Voornaam = ?, Achternaam = ?, "
                    + "Tussenvoegsel = ?, Telefoonnummer = ?, Emailadres = ?, "
                    + "Deleted = ? WHERE idKlant = ?;";
    }    
    
    @Override
    protected String getKlantPojoByIdSQL() {
        return "SELECT * FROM Klant WHERE idKlant=? AND Deleted='0';";
    }

    @Override
    protected String getDeleteKlantByIdSQL() {
        return "Update Klant SET Deleted = 1 WHERE idKlant = ?";
    }
}
