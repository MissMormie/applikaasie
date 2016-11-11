/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Klant;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.KlantPojo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public class MySQLKlantDAO extends KlantDAO {

    @Override
    public boolean createKlant(KlantPojo klant) {
        String deleted = klant.isDeleted() ? "1" : "0";
        
        String sql = "INSERT INTO Klant (Voornaam, Achternaam, Tussenvoegsel, telefoonnummer, Emailadres, Deleted) "
                + " VALUES ("
                + "'" + klant.getVoornaam()         + "', "
                + "'" + klant.getAchternaam()       + "', "
                + "'" + klant.getTussenvoegsel()    + "', "
                + "'" + klant.getTelefoonnummer()   + "', "
                + "'" + klant.getEmailadres()       + "', "
                + "'" + deleted                     + "')";
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

    private boolean tryFillPojo(ResultSet result, KlantPojo klantPojo) {
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
    public KlantPojo getKlantById(int id) {
        String sql = "SELECT * FROM Klant WHERE idKlant=" + id + ";";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        KlantPojo kp = new KlantPojo(); 
        if(tryFillPojo(result, kp)) 
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
    public boolean deleteKlantByID(int id) {
        String sql = "DELETE FROM Klant WHERE idKlant = '" + id + "';";
        return MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
    }
    
}
