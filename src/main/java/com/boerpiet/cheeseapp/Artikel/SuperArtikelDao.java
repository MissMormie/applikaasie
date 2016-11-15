/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.domeinapp.AccountPojo;
import com.boerpiet.domeinapp.ArtikelPojo;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public abstract class SuperArtikelDao {
    
    public abstract boolean createArtikel (ArtikelPojo artikel);
    
    public abstract ArtikelPojo getArtikelById (int artikelId);
    
    public abstract boolean findArtikelId (int artikelId);
    
    public abstract boolean updateArtikel (ArtikelPojo artikel);

    public abstract boolean deleteArtikel (ArtikelPojo artikel);

    public abstract boolean isValidLogin(ArtikelPojo artikel);
    
    public abstract ArrayList <ArtikelPojo> getAllArticles();
    
}
