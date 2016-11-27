/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.domeinapp.ArtikelPojo;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public class FirebirdArtikelDao extends SuperArtikelDao {

    @Override
    public boolean createArtikel(ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public int createArtikelWithReturnId (ArtikelPojo artikel) {
        throw new UnsupportedOperationException ("Not supported yet.");
    }

    @Override
    public ArtikelPojo getArtikelById (int artikelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean findArtikelId (int artikelId) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public boolean updateArtikelAll (ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean updateArtikelNaam (String naam, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean updateArtikelPrijs (double prijs, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean updateArtikelVoorraad (int voorraad, int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteArtikel(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isValidLogin(ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <ArtikelPojo> getAllArticles(){
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
