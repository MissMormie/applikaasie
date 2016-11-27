/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.BestellingPojo;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public class FirebirdBestellingDao extends SuperBestellingDao {

    @Override
    public boolean createBestelling(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override public int createBestellingWithReturnId (BestellingPojo bestelling) {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    @Override
    public BestellingPojo getBestellingById (int idBestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BestellingPojo getBestellingByKlantId (int klantId) {
        throw new UnsupportedOperationException ("Not supported yet");
    }
    
    @Override
    public boolean findBestellingId (int bestelId ) {
        throw new UnsupportedOperationException ("Not supported yet");
    }

    @Override
    public boolean updateBestelling(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteBestelling(int bestelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <BestellingPojo> getAllByKlantId (int klantId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList <BestellingModel> getBestelLijstByBestelId (int bestelId) {
           throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isValidLogin(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}