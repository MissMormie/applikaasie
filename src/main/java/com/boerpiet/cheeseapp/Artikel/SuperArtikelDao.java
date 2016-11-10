/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.domeinapp.ArtikelModel;

/**
 *
 * @author Peaq
 */
public abstract class SuperArtikelDao {
    
    public abstract boolean createArtikel (ArtikelModel artikel);
    
    public abstract ArtikelModel getArtikel (int artikelId);
    
    public abstract boolean updateArtikel (ArtikelModel artikel);

    public abstract boolean deleteArtikel (ArtikelModel artikel);

    public abstract boolean isValidLogin(ArtikelModel artikel);
    
}
