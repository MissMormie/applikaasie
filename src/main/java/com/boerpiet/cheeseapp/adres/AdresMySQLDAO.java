/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.adres;
/**
 *
 * @author Sonja
 */
public class AdresMySQLDAO extends AdresDAO {
    
    @Override
    protected String getUpdateAdresSQL() {
        return "UPDATE adres SET Straat=?, Huisnummer=?, Toevoeging=?, "
             + "Postcode=?, Woonplaats=?, Deleted=? WHERE idAdres=?;";
    } 

    @Override
    protected String getCreateAdresSQL() {
        return "INSERT INTO adres "
             + "(Straat, Huisnummer, Toevoeging, Postcode, Woonplaats, Deleted) "
             + "VALUES (?, ?, ?, ?, ?, 0);"; 
    }
    
    @Override
    protected String getCreateKlantHeeftAdresSQL() {
        return "INSERT INTO Klant_heeft_Adres (KlantId, AdresId, Adres_typeId) " +
                    "VALUES (?, ?, ?)";
    }
    
    @Override
    protected String getAdresTypeIdSQL(){
        return "SELECT idAdres_type FROM Adres_type WHERE Soort = ?";
    }
    
    @Override
    protected String getDeleteAdresSQL() {
        return "Update Adres SET Deleted = 1 WHERE idAdres = ?";
    }
    
    @Override
    protected String getDeleteKlantHeeftAdresByKlantIdSQL() {
        return "Update Klant_heeft_Adres SET Deleted = 1 WHERE KlantID = ?";
    }
    
    @Override 
    protected String getFillKlantModelWithAddressesByIdSQL() {
        return "select * from klant_heeft_adres "
                + "INNER JOIN adres ON klant_heeft_adres.AdresId = adres.idAdres "
                + "INNER JOIN adres_type ON klant_heeft_adres.Adres_typeID = Adres_type.idAdres_type "
                + "WHERE klant_heeft_adres.klantId = ?";
    }

}

