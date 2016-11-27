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
public abstract class SuperArtikelDao {
    
    public abstract boolean createArtikel (ArtikelPojo artikel);
    
    public abstract int createArtikelWithReturnId (ArtikelPojo artikel);
    
    public abstract ArtikelPojo getArtikelById (int artikelId);
    
    public abstract boolean findArtikelId (int artikelId);
        
    public abstract boolean updateArtikelAll (ArtikelPojo artikel);
    
    public abstract boolean updateArtikelNaam (String naam, int id);
    
    public abstract boolean updateArtikelPrijs (double prijs, int id);
    
    public abstract boolean updateArtikelVoorraad (int voorraad, int id);

    public abstract boolean deleteArtikel (int id);
    
    public abstract ArrayList <ArtikelPojo> getAllArticles();

    public abstract boolean isValidLogin(ArtikelPojo artikel);
    
}
