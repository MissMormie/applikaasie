/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import com.boerpiet.domeinapp.BestellingPojo;

/**
 *
 * @author Peaq
 */
public class FirebirdBestellingDao extends SuperBestellingDao {

    @Override
    public boolean createBestelling(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public boolean updateBestelling(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteBestelling(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidLogin(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}