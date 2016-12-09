/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.bestelartikel;

import com.boerpiet.dao.artikel.MimerArtikelDao;
import com.boerpiet.dao.artikel.MySqlArtikelDao;
import com.boerpiet.dao.artikel.SuperArtikelDao;

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
