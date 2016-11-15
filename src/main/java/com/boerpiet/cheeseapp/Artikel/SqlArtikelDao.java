/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Artikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.ArtikelPojo;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlArtikelDao extends SuperArtikelDao {
    
    @Override
    public boolean createArtikel(ArtikelPojo artikel) {
        String sql = "INSERT INTO Artikel (Naam, Prijs, Voorraad)"
                + " VALUES ("
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "');";
                        //+ "'" + artikel.isDeleted () + "');";
        //System.out.println(sql);
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public ArtikelPojo getArtikelById (int artikelId) {
        ArtikelPojo a = new ArtikelPojo ();
        String sql = "SELECT * FROM Artikel" + " WHERE idArtikel = "+artikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
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
    public boolean findArtikelId (int artikelId) {
        String sql = "SELECT * FROM Artikel" + " WHERE idArtikel = " + artikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
        if (rs == null) {
            return false;
        } else {
            return true;
        }
        } catch (Exception ex) {
                return false;
                }
        }

    @Override
    public boolean updateArtikel (ArtikelPojo artikel) {
        String sql = "UPDATE Artikel SET (Naam, Prijs, Voorraad)"
                + " VALUES ("
                        //+ "'" + artikel.getId () + "',"
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "');";
                        //+ "'" + artikel.isDeleted () + "');";
        //System.out.println(sql);
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql); //syntax error
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteArtikel (ArtikelPojo artikel) {
        String sql = "DELETE * FROM Artikel" + " WHERE ArtikelId = "+artikel.getId ();
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean isValidLogin (ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public ArrayList <ArtikelPojo> getAllArticles() {
        String sql = "SELECT * FROM Artikel WHERE Deleted = 0";
        ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
        ArrayList <ArtikelPojo> list = new ArrayList <>();
        
        if (result == null) {
            System.out.println("Geen artikelen gevonden.");
        }
        try {
            while (result.next()) {       
                ArtikelPojo am = new ArtikelPojo();
                fillPojo (result, am);
                list.add(am);
            }
        }
            catch (SQLException ex) {
                    System.out.println(ex);
                    }
        return list;
    }
    
    private void fillPojo (ResultSet result, ArtikelPojo artikelPojo) throws SQLException {
        artikelPojo.setId (result.getInt ("idArtikel"));
        artikelPojo.setNaam (result.getString ("Naam"));
        artikelPojo.setPrijs (result.getDouble("Prijs"));
        artikelPojo.setVoorraad (result.getInt("Voorraad"));
    }
}
