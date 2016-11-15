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
    public ArtikelPojo getArtikelById (int artikelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean findArtikelId (int artikelId) {
        throw new UnsupportedOperationException("Not supported yet");
    }

    @Override
    public boolean updateArtikel(ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteArtikel(ArtikelPojo artikel) {
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
