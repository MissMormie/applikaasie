/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import java.sql.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peaq
 */
public class SqlBestelArtikelDao extends SuperBestelArtikelDao {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean createBestelArtikel(BestelArtikelPojo bArtikel) {
        String sql = "INSERT INTO BestelArtikel (BestellingId, ArtikelId, Aantal)"
                + " VALUES ("
                        + "'" + bArtikel.getBestelId    () + "',"
                        + "'" + bArtikel.getArtikelId   () + "',"
                        + "'" + bArtikel.getAantal      () + "');";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
            } catch (Exception ex) {
                logger.error ("Bestelregel is niet gemaakt: "+ ex);
            return false;
        }
        return true;
    }
    
    @Override
    public BestelArtikelPojo getBestelArtikelById (int bArtikelId) {
        BestelArtikelPojo ba = new BestelArtikelPojo ();
        String sql = "SELECT * FROM BestelArtikel" + " WHERE Deleted = 0 AND Bezorgd = 0 "
                   + "AND idBestelArtikel = "+bArtikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
        ba.setId (rs.getInt(1));
        ba.setBestelId (rs.getInt(2));
        ba.setArtikelId (rs.getInt(3));
        ba.setAantal (rs.getInt(4));
        } catch (Exception ex) {
            logger.warn ("Kon bestelregel niet vinden: "+ex);
            return null;
        }
        return ba;
    }
    
    @Override
    public BestelArtikelPojo getBestelArtikelByBestelId (int bestelId) {
        BestelArtikelPojo ba = new BestelArtikelPojo ();
        String sql = "SELECT (ArtikelId, Aantal) "
                   + "WHERE Deleted = 0 AND Bezorgd = 0 AND BestellingId = " + bestelId;
        try {
            ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            ba.setArtikelId (rs.getInt(1));
            ba.setAantal(rs.getInt(2));
        } catch (Exception ex) {
            logger.warn ("Kon bestelregel bij dit bestelid niet vinden: "+ex);
            return null;
        }
        return ba;
    }
    
    @Override
    public boolean findBestelArtikel (int baId) {
        String sql = "SELECT idBestelArtikel FROM BestelArtikel "+
                "WHERE Deleted = 0 AND Bezorgd = 0 AND idBestelArtikel = "+ baId;
        //System.out.println(sql);
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read (sql);
            return rs != null;
        } catch (Exception ex) {
            logger.warn ("Kon bestelregel niet vinden: "+ex);
                return false;
            }        
    }
    
    @Override
    public int getMaxBestelArtikelId() {
        String sql = "SELECT idBestelArtikel FROM BestelArtikel WHERE idBestelArtikel  = "
                + "(SELECT MAX(idBestelArtikel) FROM BestelArtikel)";
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            int max = 0;
            if (rs.next()) {
                max = rs.getInt(1);
            } return max;
        } catch (Exception ex) {
            logger.warn ("Bestelregel is niet in database "+ ex);
            return 0;
        }
    }
    
    @Override
    public ArrayList <BestelArtikelPojo> getBestelLijstByBestelId (int bestelId) {
        String sql = "SELECT BestelArtikel.idBestelArtikel, BestelArtikel.ArtikelId, BestelArtikel.Aantal "
                + "FROM BestelArtikel "
                + "INNER JOIN Artikel ON BestelArtikel.ArtikelId = Artikel.idArtikel "
                + "WHERE BestelArtikel.Bezorgd = 0 AND BestelArtikel.BestellingId = "+ bestelId;
        ResultSet result  = MySQLConnection.getMySQLConnection().read(sql);
        ArrayList <BestelArtikelPojo> list = new ArrayList<>();
        if (result==null) {
          System.out.println("Kan de gevraagde selectie niet vinden.");
        }
        try {
            while (result.next()) {
                BestelArtikelPojo ba = new BestelArtikelPojo();
                fillModelBestelId (result, ba);
                list.add(ba);
            }
        } catch (SQLException ex) {
            logger.error ("Kon geen lijst van bestelregels maken met dit bestelid: "+ex);
            }
        return list;
    }
    
    private void fillModelBestelId (ResultSet rs, BestelArtikelPojo ba) throws SQLException {
        ba.setId(rs.getInt("idBestelArtikel"));
        ba.setArtikelId(rs.getInt("ArtikelId"));
        ba.setAantal(rs.getInt("Aantal"));
        
    }

    @Override
    public boolean updateBestelArtikel(BestelArtikelPojo bArtikel, int regelId) {
                
        String sql = "UPDATE BestelArtikel SET "
                        + "ArtikelId = '" + bArtikel.getArtikelId() + "', "
                        + "Aantal = '" + bArtikel.getAantal() + "'"
                        + "WHERE idBestelArtikel = " + regelId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            logger.error ("Wijziging van bestelregel is mislukt: "+ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelArtikel(int brId) {
        String sql = "UPDATE BestelArtikel SET Deleted = 1, Bezorgd = 1 "
                //als deleted op 1 staat dan is bezorgd =1 noodzakelijk voor het
                //laten zien van actuele lijst in view, maar het is niet noodzakelijk
                //daadwerkelijk bezorgd
                    + "WHERE Deleted = 0 AND idBestelArtikel = "+brId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            logger.error ("Verwijderen van bestelregel is mislukt: "+ex);
            return false;
        }
        return true;
    }
    //verwijdering van artikel zonder return value
    @Override
    public void deleteArticleFromOrder (int brId) {
        String sql = "UPDATE BestelArtikel SET Deleted = 1, Bezorgd = 1 "
                    + "WHERE Deleted = 0 AND idBestelArtikel = "+brId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            logger.error ("Verwijderen van bestelregel (void methode) is mislukt: "+ex);
        }
    }
}