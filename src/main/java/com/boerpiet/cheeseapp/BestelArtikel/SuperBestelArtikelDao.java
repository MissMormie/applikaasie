/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingModel;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public abstract class SuperBestelArtikelDao {
    
    public abstract boolean createBestelArtikel (BestelArtikelPojo bArtikel);
    
    public abstract BestelArtikelPojo getBestelArtikelById (int bArtikelId);
    
    public abstract BestelArtikelPojo getBestelArtikelByBestelId (int bestelId);
    
    public abstract ArrayList <BestelArtikelPojo> getBestelLijstByBestelId (int bestelId);
    
    public abstract boolean updateBestelArtikel (BestelArtikelPojo bArtikel, int regelId);

    public abstract boolean deleteBestelArtikel (BestelArtikelPojo bArtikel);

    public abstract boolean isValidLogin(BestelArtikelPojo bArtikel);
    
}