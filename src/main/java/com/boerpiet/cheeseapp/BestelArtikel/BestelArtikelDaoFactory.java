/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.cheeseapp.Artikel.FirebirdArtikelDao;
import com.boerpiet.cheeseapp.Artikel.SqlArtikelDao;
import com.boerpiet.cheeseapp.Artikel.SuperArtikelDao;

/**
 *
 * @author Peaq
 */
public class BestelArtikelDaoFactory {
    public static SuperBestelArtikelDao getBestelArtikelDAO(String type){
            if (type.equals("MySQL")) {
                    return new SqlBestelArtikelDao();
            } else if (type.equals("Firebird")) {
                    return new FirebirdBestelArtikelDao();
            } 
            return new SqlBestelArtikelDao();
    }
}
