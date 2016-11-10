/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;
/**
 *
 * @author Peaq
 */
public class ArtikelDaoFactory {
    public static SuperArtikelDao getArtikelDAO(String type){
            if (type.equals("MySQL")) {
                    return new SqlArtikelDao();
            } else if (type.equals("Firebird")) {
                    return new FirebirdArtikelDao();
            } 
            return new SqlArtikelDao();
    }    
}
