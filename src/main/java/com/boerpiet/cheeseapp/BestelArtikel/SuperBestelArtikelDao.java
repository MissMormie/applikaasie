/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.domeinapp.BestelArtikelModel;

/**
 *
 * @author Peaq
 */
public abstract class SuperBestelArtikelDao {
    
    public abstract boolean createBestelArtikel (BestelArtikelModel bArtikel);
    
    public abstract BestelArtikelModel getBestelArtikel (int bArtikelId);
    
    public abstract boolean updateBestelArtikel (BestelArtikelModel bArtikel);

    public abstract boolean deleteBestelArtikel (BestelArtikelModel bArtikel);

    public abstract boolean isValidLogin(BestelArtikelModel bArtikel);
    
}