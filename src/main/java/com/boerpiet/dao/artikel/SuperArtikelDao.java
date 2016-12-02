/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.artikel;

import com.boerpiet.dao.Connector;
import com.boerpiet.domeinapp.ArtikelPojo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peaq
 */
public abstract class SuperArtikelDao {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    protected abstract String getCreateArtikelMySQL ();
    
    protected abstract String getCreateArtikelMimerSQL ();
    
    protected abstract String getMaxIdArtikelSQL ();
    
    public abstract int createArtikelWithReturnId (ArtikelPojo artikel);
    
    public abstract ArtikelPojo getArtikelById (int artikelId);
    
    public abstract boolean findArtikelId (int artikelId);
    
    public abstract int getMaxArtikelId ();
        
    public abstract boolean updateArtikelAll (ArtikelPojo artikel);
    
    public abstract boolean updateArtikelNaam (String naam, int id);
    
    public abstract boolean updateArtikelPrijs (double prijs, int id);
    
    public abstract boolean updateArtikelVoorraad (int voorraad, int id);

    public abstract boolean deleteArtikel (int id);
    
    public abstract ArrayList <ArtikelPojo> getAllArticles();

//method calls    
    public boolean createArtikelMySQL (ArtikelPojo artikel) {
        try (Connection conn = Connector.getConnection()) {
            String sql = getCreateArtikelMySQL();
            PreparedStatement pstmt = conn.prepareStatement (sql);
            
            pstmt.setString (1, artikel.getNaam());
            pstmt.setDouble (2, artikel.getPrijs());
            pstmt.setInt (3, artikel.getVoorraad());
            
            if( pstmt.executeUpdate() == 0) {
                throw new Exception("Adding article to database failed.");
            }            
            return true;

        } catch (Exception ex) {
            logger.error("Create article failed: " + ex );
            return false;
            }
        }
    
    public boolean createArtikelMimerSQL (ArtikelPojo artikel) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getCreateArtikelMimerSQL();
            PreparedStatement pstmt = conn.prepareStatement (sql);
            
            artikel.setId(getMaxIdArtikel(artikel)+1);
            pstmt.setInt (1, artikel.getId());
            pstmt.setString (2, artikel.getNaam());
            pstmt.setDouble (3, artikel.getPrijs());
            pstmt.setInt (4, artikel.getVoorraad());
            
            if (pstmt.executeUpdate() == 0) {
                throw new Exception ("Adding article to database failed.");
            }
            return true;
        } catch (Exception ex) {
            logger.error ("Create article failed: "+ex);
            return false;
        }
    }
    
    private int getMaxIdArtikel (ArtikelPojo artikel) {
        try (Connection conn = Connector.getConnection()) {
            String sql = getMaxIdArtikelSQL();
            PreparedStatement pstmt = conn.prepareStatement (sql);
            if(pstmt.executeQuery() == null) {
                throw new Exception ("Niet gevonden.");
                }
            //pstmt.setInt(1, artikel.getId());
            int max = artikel.getId();
            return max;
            }
        catch (Exception ex) {
            logger.error ("Geen artikelen gevonden: "+ex);
            return 0;
        }
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
        String sql = "SELECT idArtikel FROM Artikel WHERE idArtikel = (SELECT MAX(idArtikel) FROM Artikel)";
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            int max = 0;
            if (rs.next()) {
                max = rs.getInt(1);
            }
            return max;
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

