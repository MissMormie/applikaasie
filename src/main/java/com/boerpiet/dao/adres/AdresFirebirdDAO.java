/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.adres;



/**
 *
 * @author Sonja
 */
public class AdresFirebirdDAO extends AdresDAO {

    @Override
    protected String getUpdateAdresSQL() {
        return "UPDATE \"adres\" SET \"Straat\"=?,"
                    + " \"Huisnummer\"=?, \"Toevoeging\"=?, \"Postcode\"=?, "
                    + "\"Woonplaats\"=?, \"Deleted\"=? WHERE \"idAccount\"=?;";
    } 

    @Override
    protected String getCreateAdresSQL() {
        return "INSERT INTO \"adres\" "
                    + "(\"Straat\", \"Huisnummer\", \"Toevoeging\", \"Postcode\", \"Woonplaats\", \"Deleted\") "
                    + "VALUES (?, ?, ?, ?, ?, 0);";
    }
    
    @Override
    protected String getCreateKlantHeeftAdresSQL() {
        return "INSERT INTO \"klant_heeft_adres\" (\"KlantId\", \"AdresId\", \"Adres_typeId\") " +
                    "VALUES (?, ?, ?)";
    }
    
    @Override
    protected String getAdresTypeIdSQL(){
        return "SELECT \"idAdres_type\" FROM \"adres_type\" WHERE \"Soort\" = ?";
    }
    
    @Override
    protected String getDeleteAdresSQL() {
        return "Update \"adres\" SET \"Deleted\" = 1 WHERE \"idAdres\" = ?";
    }
    
    @Override
    protected String getDeleteKlantHeeftAdresByKlantIdSQL() {
        return "Update \"klant_heeft_adres\" SET \"Deleted\" = 1 WHERE \"KlantID\" = ?";
    }
    
    @Override 
    protected String getFillKlantModelWithAddressesByIdSQL() {
        return "SELECT * FROM \"klant_heeft_adres\" " +
                "JOIN \"adres\" ON \"klant_heeft_adres\".\"AdresId\" = \"adres\".\"idAdres\" " +
                "JOIN \"adres_type\" ON \"klant_heeft_adres\".\"Adres_typeId\" = \"adres_type\".\"idAdres_type\" " +
                "WHERE \"klant_heeft_adres\".\"KlantId\" = ?;";
    }
}
