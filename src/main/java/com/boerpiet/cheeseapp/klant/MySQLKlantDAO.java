/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.klant;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public class MySQLKlantDAO extends KlantDAO {

    
    // TODO add create address
    @Override
    public boolean createKlant(KlantModel klant) {
        String deleted = klant.getKlantPojo().isDeleted() ? "1" : "0";
        
        String sql = "INSERT INTO Klant (Voornaam, Achternaam, Tussenvoegsel, telefoonnummer, Emailadres, Deleted) "
                + " VALUES ("
                + "'" + klant.getKlantPojo().getVoornaam()         + "', "
                + "'" + klant.getKlantPojo().getAchternaam()       + "', "
                + "'" + klant.getKlantPojo().getTussenvoegsel()    + "', "
                + "'" + klant.getKlantPojo().getTelefoonnummer()   + "', "
                + "'" + klant.getKlantPojo().getEmailadres()       + "', "
                + "'" + deleted                                    + "')";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }

    @Override
    public ArrayList<KlantPojo> getAllKlanten() {
        String sql =  "SELECT * FROM Klant WHERE Deleted = 0";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        
        ArrayList<KlantPojo> list = new ArrayList<>();
        
        try {
            while(result.next()) {
                KlantPojo kp = new KlantPojo(); 
                fillPojo(result, kp);
                list.add(kp);
            }
             
        } catch (SQLException ex) { 
            // TODO logger
            System.out.println("SQL Exception: " + ex);
        }
        return list;

    }

    private boolean tryFillKlantPojo(ResultSet result, KlantPojo klantPojo) {
        try {
            result.next();
            fillPojo(result, klantPojo);
            return true; 
        } catch (SQLException ex) { 
            return false;
        }        
    }
    
    private void fillPojo(ResultSet result, KlantPojo kp) throws SQLException {
        kp.setId(result.getInt("idKlant"));
        kp.setVoornaam(result.getString("Voornaam"));
        kp.setAchternaam(result.getString("Achternaam"));
        kp.setTussenvoegsel(result.getString("Tussenvoegsel"));
        kp.setTelefoonnummer(result.getString("Telefoonnummer"));
        kp.setEmailadres(result.getString("Emailadres"));
        kp.setDeleted((boolean)result.getBoolean("Deleted"));
    }

    @Override
    public KlantModel getKlantById(int id) {
        KlantPojo kp = getKlantPojoById(id);
        if(kp == null)
            return null;
        KlantModel skm = new KlantModel(kp);
        fillSingleKlantModelWithAddressesById(skm);
        return skm; 
    }
    
    private boolean fillSingleKlantModelWithAddressesById(KlantModel skm) {
        String sql = "select * from klant_heeft_adres "
                + "INNER JOIN adres ON klant_heeft_adres.AdresId = adres.idAdres "
                + "INNER JOIN adres_type ON klant_heeft_adres.Adres_typeID = Adres_type.idAdres_type "
                + "WHERE klant_heeft_adres.klantId = '" + skm.getKlantPojo().getId() +"'";
        
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        try {
            while(result.next()) {
                skm.setAdres(createAdresPojo(result));
            }
        } catch (SQLException ex) { 
            return false;
        }
        return true;
    }
    
    
    private AdresPojo createAdresPojo(ResultSet result) throws SQLException {
        AdresPojo ap = new AdresPojo(result.getInt("idAdres"),
                                     result.getString("Straat"),
                                     result.getInt("Huisnummer"),
                                     result.getString("Toevoeging"),
                                     result.getString("Woonplaats"),
                                     (boolean)result.getBoolean("Deleted"),
                                     result.getString("Soort"));

        return ap;
    }
    
    private KlantPojo getKlantPojoById(int id) {
        String sql = "SELECT * FROM Klant WHERE idKlant=" + id + ";";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        KlantPojo kp = new KlantPojo(); 
        if(tryFillKlantPojo(result, kp)) 
            return kp;
        else 
            return null;
        
    }

    @Override
    public boolean updateKlantById(KlantPojo klant) {
        if (klant.getId() == 0)
            return false;
        
        String deleted = klant.isDeleted() ? "1" : "0";
        
        String sql = "Update Klant SET "
                + "Voornaam = '"        + klant.getVoornaam()       + "', "
                + "Achternaam = '"      + klant.getAchternaam()     + "', "
                + "Tussenvoegsel = '"   + klant.getTussenvoegsel()  + "', "
                + "Telefoonnummer = '"  + klant.getTelefoonnummer() + "', "
                + "Emailadres = '"      + klant.getEmailadres()     + "', "
                + "Deleted = '"         + deleted                   + "' "
                + "WHERE idKlant = "    + klant.getId()             + ";";
        
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);       
    }

    @Override
    public boolean deleteKlantById(int id) {
        String sql = "Update Klant SET Deleted = 1 WHERE idKlant = '"+ id +"'";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }

    // TODO make it works with SQL transactions to ensure everything is deleted or nothing.
    // currently only validation on delete klant.
    @Override
    public boolean deleteKlant(KlantModel klant) {
        
        // delete bijbehorende adressen. Check of deze al gevuld zijn, zo niet, doe dit eerst
        if(klant.getBezorgAdresPojo()   == null || klant.getFactuurAdresPojo()  == null ||
           klant.getPostAdresPojo()     == null ) {
            fillSingleKlantModelWithAddressesById(klant);
        }
        
        deleteAdres(klant.getBezorgAdresPojo());
        deleteAdres(klant.getFactuurAdresPojo());
        deleteAdres(klant.getPostAdresPojo());
        
        deleteKlantHeeftAdres(klant.getKlantPojo().getId());
        
        // delete klant informatie
        String sql = "Update Klant SET Deleted = 1 "
                   + "WHERE idKlant = '"+ klant.getKlantPojo().getId() +"'";
        
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }
    
    private boolean deleteKlantHeeftAdres(int id) {
        String sql = "Update Klant_heeft_Adres SET Deleted = 1 "
                   + "WHERE KlantID = '"+ id +"'";
        
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);        
    }
    
    private boolean deleteAdres(AdresPojo adres) {
        String sql = "Update Adres SET Deleted = 1 "
                   + "WHERE idAdres = '"+ adres.getIdAdres()+"'";
        
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);        
    }
    
}
