/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.ArtikelModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlArtikelDao extends SuperArtikelDao {
    
    @Override
    public boolean createArtikel(ArtikelModel artikel) {
        String sql = "INSERT INTO Artikel (id, naam, prijs, voorraad, deleted)"
                + " VALUES ("
                        + "'" + artikel.getId () + "',"
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "',"
                        + "'" + artikel.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public ArtikelModel getArtikel (int artikelId) {
        ArtikelModel a = new ArtikelModel ();
        String sql = "SELECT * FROM Artikel" + "WHERE ArtikelId = "+artikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().getResult(sql);
        a.setId (rs.getInt(1));
        a.setNaam(rs.getString(2));
        a.setPrijs (rs.getDouble(3));
        a.setVoorraad(rs.getInt(4));
        a.setDeleted (rs.getBoolean(5));       
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return a;
    }

    @Override
    public boolean updateArtikel (ArtikelModel artikel) {
        String sql = "UPDATE Artikel SET (id, naam, prijs, voorraad, deleted)"
                + " VALUES ("
                        + "'" + artikel.getId () + "',"
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "',"
                        + "'" + artikel.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteArtikel (ArtikelModel artikel) {
        String sql = "DELETE * FROM Artikel" + "WHERE ArtikelId = "+artikel.getId ();
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean isValidLogin (ArtikelModel artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
