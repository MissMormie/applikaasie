/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.bestelartikel;

import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingModel;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public class FirebirdBestelArtikelDao extends SuperBestelArtikelDao {

    @Override
    public boolean createBestelArtikel(BestelArtikelPojo bArtikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BestelArtikelPojo getBestelArtikelById (int bArtikelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public BestelArtikelPojo getBestelArtikelByBestelId (int bestelId) {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
    
    @Override
    public boolean findOAIdByOrderId (int bestelId, int baId) {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
    
    @Override
    public int getMaxBestelArtikelId() {
        throw new UnsupportedOperationException ("Not supported yet.");
    }
    
    @Override
    public ArrayList <BestelArtikelPojo> getBestelLijstByBestelId (int bestelId) {
      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.  
    }

    @Override
    public boolean updateBestelArtikel(BestelArtikelPojo bArtikel, int regelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteBestelArtikel(int brId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void deleteArticleFromOrder (int brId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
