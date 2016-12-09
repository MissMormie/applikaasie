/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.artikel;

import com.boerpiet.dao.Connector;
import com.boerpiet.domeinapp.ArtikelPojo;
import com.boerpiet.utility.ConfigVars;
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
    //DecimalFormat df = new DecimalFormat("#.00");
    
    // utility strings
    protected abstract String getIsArtikelInDatabaseSQL ();
    
    protected abstract String getArtikelByIdSQL ();
    
    protected abstract String getMaxArtikelIdSQL ();
        
    protected abstract String getArtikelForArrayListSQL ();
    
    // CRUD strings  
    protected abstract String getCreateArtikelMySQL ();
    
    protected abstract String getCreateArtikelMimerSQL ();
    
    protected abstract String getUpdateArtikelNPVSQL ();
    
    protected abstract String getUpdateArtikelNaamSQL ();
    
    protected abstract String getUpdateArtikelPrijsSQL ();
    
    protected abstract String getUpdateArtikelVoorraadSQL ();

    protected abstract String getDeleteArtikelSQL ();
    
    // utility methods    
    public boolean isArtikelInDatabase (int artikelId) {
        
        try (Connection conn = Connector.getConnection()) {
            
            String sql = getArtikelByIdSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);            
            pstmt.setInt (1, artikelId);
            
            ResultSet result = pstmt.executeQuery();
            return result.next();
        } catch (Exception ex) {
            logger.warn ("Article not found: "+ ex);
        }
        return false;
    }
    
    public int getMaxIdArtikel () {
        int max = 0;
        try (Connection conn = Connector.getConnection()) {
            String sql = getMaxArtikelIdSQL();
            PreparedStatement pstmt = conn.prepareStatement (sql);
      
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                max = rs.getInt(1); //probably nullpointer as it is?
            }
        }
        catch (Exception ex) {
            logger.error ("Geen artikelen gevonden: "+ex);
            return 0;
        }
        return max;
    }
    
    public ArtikelPojo getArtikelById (int id) {
        ArtikelPojo ap  = new ArtikelPojo ();
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getArtikelByIdSQL ();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt (1, id);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                fillPojo (rs, ap);
            }
        } catch (SQLException ex) {
            logger.error("Artikel niet gevonden." +ex);
            return null;
        }
        return ap;
    }
    
    public ArrayList <ArtikelPojo> getAllArticles() {
        
        ArrayList <ArtikelPojo> list = new ArrayList <>();
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getArtikelForArrayListSQL ();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {       
                ArtikelPojo am = new ArtikelPojo();
                fillPojo (rs, am);
                list.add(am);
                if (list.isEmpty()) {
                    return null;
                }
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
    
    // CRUD methods    
    public int createArtikelWithReturnId (ArtikelPojo artikel) {            
            //MySQL and Mimer need different handling of creating an article
            //because Mimer free version can only handle one statement per query
            //and trigger for auto-increment needs (to be) a sequence for which
            //databank rights are needed (which seemingly cannot be granted to
            //development user.
            String type = ConfigVars.getDbType();
            
            if (type.equals("MySQL")) {
                return createArticleWithReturnIdMySQL(artikel);
            } else {
                if (type.equals("Mimer")) {
                    return returnCreatedArticleIdMimerSQL(artikel);
            }
        } return 0;
    }
    
    private int createArticleWithReturnIdMySQL (ArtikelPojo artikel) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getCreateArtikelMySQL();
            PreparedStatement pstmt = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS);
            
            pstmt.setString (1, artikel.getNaam());
            pstmt.setDouble (2, artikel.getPrijs());
            pstmt.setInt (3, artikel.getVoorraad());
            
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException ex) {
            logger.error ("Create article failed: "+ex);
            return 0;
        }
    }
    
    private boolean createArtikelMimerSQL (ArtikelPojo artikel) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getCreateArtikelMimerSQL();
            PreparedStatement pstmt = conn.prepareStatement (sql);
            
            artikel.setId(getMaxIdArtikel()+1); //workaround for auto-increment which Mimer does not have
            pstmt.setInt (1, artikel.getId());
            pstmt.setString (2, artikel.getNaam());
            pstmt.setDouble (3, artikel.getPrijs());
            pstmt.setInt (4, artikel.getVoorraad());
            
            if (pstmt.executeUpdate() == 0) {
                throw new SQLException();
            }
            return true;
        } catch (Exception ex) {
            logger.error ("Create article failed: "+ex);
            return false;
        }
    }
    
    private int returnCreatedArticleIdMimerSQL (ArtikelPojo artikel) {
        
        if (createArtikelMimerSQL(artikel)) {
            return getMaxIdArtikel();
        } else {
            return 0;
        }
    }
    
    public boolean updateArtikelNPV (ArtikelPojo artikel) {

        try (Connection conn = Connector.getConnection()) {
            String sql = getUpdateArtikelNPVSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString (1, artikel.getNaam());
            pstmt.setDouble (2, artikel.getPrijs());
            pstmt.setInt (3, artikel.getVoorraad());
            pstmt.setInt (4, artikel.getId());
            
            pstmt.executeUpdate();
        }            
        catch (Exception ex) {
            logger.error ("Wijzigen van artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }
    
    public boolean updateArtikelNaam (String naam, int id) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getUpdateArtikelNaamSQL ();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString (1, naam);
            pstmt.setInt (2, id);
            
            pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.error ("Wijzigen van naam artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }
    
    public boolean updateArtikelPrijs (double prijs, int id) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getUpdateArtikelPrijsSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setDouble (1, prijs);
            pstmt.setInt (2, id);
            
            pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.error ("Wijzigen van prijs artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }
    
    public boolean updateArtikelVoorraad (int voorraad, int id) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getUpdateArtikelVoorraadSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt (1, voorraad);
            pstmt.setInt (2, id);
            
            pstmt.executeUpdate();
            
        } catch (Exception ex) {
            logger.error ("Wijzigen van voorraad artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }   

    public boolean deleteArtikel (int id) {
        
        try (Connection conn = Connector.getConnection()) {
            String sql = getDeleteArtikelSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            pstmt.executeUpdate();
        } catch (Exception ex) {
            logger.error ("Verwijderen van artikel is mislukt: "+ex);
            return false;
        }
        return true;
    }
}
