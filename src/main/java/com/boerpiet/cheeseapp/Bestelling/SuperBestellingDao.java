/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import com.boerpiet.domeinapp.BestellingModel;

/**
 *
 * @author Peaq
 */
public abstract class SuperBestellingDao {
    
    public abstract boolean createBestelling (BestellingModel bestelling);
    
    public abstract BestellingModel getBestelling (int idBestelling);
    
    public abstract boolean updateBestelling (BestellingModel bestelling);

    public abstract boolean deleteBestelling (BestellingModel bestelling);

    public abstract boolean isValidLogin(BestellingModel bestelling);
    
}