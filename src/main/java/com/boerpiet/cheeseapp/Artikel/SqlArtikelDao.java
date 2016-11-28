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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peaq
 */
public class SqlArtikelDao extends SuperArtikelDao {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public boolean createArtikel(ArtikelPojo artikel) {
        String sql = "INSERT INTO Artikel (Naam, Prijs, Voorraad)"
                + " VALUES ("
                        + "'" + artikel.getNaam () + "',"
                        + "'" + artikel.getPrijs () + "',"
                        + "'" + artikel.getVoorraad () + "');";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            logger.error ("Aanmaken van nieuw artikel is mislukt: "+ ex);
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
            logger.error ("Aanmaken van nieuw artikel is mislukt: "+ex);
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
                logger.warn ("Artikel niet gevonden: "+ex);
                }
        return ap;
    }    
    
    @Override
    public boolean findArtikelId (int artikelId) {
        String sql = "SELECT * FROM Artikel" + " WHERE Deleted = 0 AND idArtikel = " + artikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
            return rs != null; 
        } catch (Exception ex) {
            logger.warn ("Artikel niet gevonden: "+ ex);
                return false;
                }
        }
    
    @Override
    public int getMaxArtikelId () {
        String sql = "SELECT MAX (idArtikel) FROM Artikel";
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
        return rs.getInt(1);
        } catch (Exception ex) {
            logger.warn ("Artikelid is niet in database "+ ex);
            return 0;
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
            logger.error ("Wijzigen van artikel is mislukt: "+ex);
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
            logger.error ("Wijzigen van naam artikel is mislukt: "+ex);
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
            logger.error ("Wijzigen van prijs artikel is mislukt: "+ex);
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
            logger.error ("Wijzigen van voorraad artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }   

    @Override
    public boolean deleteArtikel (int id) {
        String sql = "UPDATE Artikel SET Deleted = 1 WHERE idArtikel = " + id;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            logger.error ("Verwijderen van artikel is mislukt: "+ex);
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
                logger.warn ("Kon geen lijst van artikelen maken: "+ex);
            }
        return list;
    }
        
    private void fillPojo (ResultSet result, ArtikelPojo artikelPojo) throws SQLException {
        artikelPojo.setId (result.getInt ("idArtikel"));
        artikelPojo.setNaam (result.getString ("Naam"));
        artikelPojo.setPrijs (result.getDouble ("Prijs"));
        artikelPojo.setVoorraad (result.getInt("Voorraad"));
    }
 }
