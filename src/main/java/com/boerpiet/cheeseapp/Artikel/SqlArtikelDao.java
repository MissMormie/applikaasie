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
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public int createArtikelWithReturnId (ArtikelPojo artikel) {
        String sql = "INSERT INTO Artikel (Naam, Prijs, Voorraad)"
                + " VALUES ("
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "');";
        try {
            int key = MySQLConnection.getMySQLConnection().createAndReturnID(sql);
            return key;
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Er is iets misgegaan, artikel is niet aangemaakt.");
            return 0;
        }
    }
    
    @Override
    public ArtikelPojo getArtikelById (int artikelId) {
        ArtikelPojo ap = new ArtikelPojo ();
        String sql = "SELECT * FROM Artikel " + "WHERE Deleted = 0 AND idArtikel = "+artikelId;

        try {
            ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
            if (rs.next()) {
                fillPojo (rs, ap);
            }
        }
            catch (SQLException ex) {
                System.out.println("Artikel niet gevonden");
                    System.out.println(ex);
                    }
        return ap;
    }    
    
    @Override
    public boolean findArtikelId (int artikelId) {
        String sql = "SELECT * FROM Artikel" + " WHERE Deleted = 0 AND idArtikel = " + artikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
            return rs != null;
        } catch (Exception ex) {
                return false;
                }
        }
    
    @Override
    public boolean updateArtikelAll (ArtikelPojo artikel) {
        String sql = "UPDATE Artikel SET "
                + "Naam = '" + artikel.getNaam()+ "', "
                + "Prijs = '" + artikel.getPrijs() + "', "
                + "Voorraad = '" + artikel.getVoorraad () + "'"
                + "WHERE idArtikel = '"+artikel.getId()+ "';";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean updateArtikelNaam (String naam, int id) {
        String sql = "UPDATE Artikel SET Naam = '" + naam + "' "
                   + "WHERE idArtikel = " + id;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql); //syntax error
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean updateArtikelPrijs (double prijs, int id) {
        String sql = "UPDATE Artikel SET Prijs = '" + prijs + "' "
                   + "WHERE idArtikel = " + id;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql); //syntax error
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean updateArtikelVoorraad (int voorraad, int id) {
        String sql = "UPDATE Artikel SET Voorraad = '" + voorraad + "' "
                   + "WHERE idArtikel = " + id;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql); //syntax error
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }   

    @Override
    public boolean deleteArtikel (int id) {
        String sql = "UPDATE Artikel SET Deleted = 1 WHERE idArtikel = " + id;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
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
        artikelPojo.setPrijs (result.getDouble ("Prijs"));
        artikelPojo.setVoorraad (result.getInt("Voorraad"));
    }
    
    @Override
    public boolean isValidLogin (ArtikelPojo artikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
