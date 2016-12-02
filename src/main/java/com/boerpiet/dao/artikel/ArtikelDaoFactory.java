/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.artikel;
/**
 *
 * @author Peaq
 */
public class ArtikelDaoFactory {
    public static SuperArtikelDao getArtikelDAO(String type){
            if (type.equals("MySQL")) {
                    return new MySqlArtikelDao();
            } else if (type.equals("Mimer")) {
                    return new MimerArtikelDao();
            } 
            return new MySqlArtikelDao();
    }    
}
