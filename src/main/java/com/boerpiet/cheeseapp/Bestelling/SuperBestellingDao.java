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
public abstract class SuperBestellingDao {
    
    public abstract boolean createBestelling (BestellingPojo bestelling);
    
    public abstract BestellingPojo getBestellingById (int idBestelling);
    
    public abstract BestellingPojo getBestellingByKlantId (int klantId);
        
    public abstract boolean updateBestelling (BestellingPojo bestelling);

    public abstract boolean deleteBestelling (BestellingPojo bestelling);

    public abstract boolean isValidLogin(BestellingPojo bestelling);
    
}