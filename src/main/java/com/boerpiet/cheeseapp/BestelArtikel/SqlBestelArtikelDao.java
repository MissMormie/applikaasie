/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.BestellingPojo;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlBestelArtikelDao extends SuperBestelArtikelDao {

    @Override
    public boolean createBestelArtikel(BestelArtikelPojo bArtikel) {
        String sql = "INSERT INTO BestelArtikel (BestellingId, ArtikelId, Aantal)"
                + " VALUES ("
                        + "'" + bArtikel.getBestelId    () + "',"
                        + "'" + bArtikel.getArtikelId   () + "',"
                        + "'" + bArtikel.getAantal      () + "');";
                        //+ "'" + bArtikel.isDeleted  () + "');";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
            } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
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
        //ba.setDeleted (rs.getBoolean(5));       
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return ba;
    }
    
    @Override
    public BestelArtikelPojo getBestelArtikelByBestelId (int bestelId) {
        BestelArtikelPojo ba = new BestelArtikelPojo ();
        String sql = "SELECT (ArtikelId, Aantal) " + "WHERE Deleted = 0 AND Bezorgd = 0 AND BestellingId = " + bestelId;
        try {
            ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            ba.setArtikelId (rs.getInt(1));
            ba.setAantal(rs.getInt(2));
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return ba;
    }
    
    @Override
    public ArrayList <BestelArtikelPojo> getBestelLijstByBestelId (int bestelId) {
        String sql = "SELECT BestelArtikel.idBestelArtikel, BestelArtikel.ArtikelId, BestelArtikel.Aantal "
                + "FROM BestelArtikel "
                + "INNER JOIN Artikel ON BestelArtikel.ArtikelId = Artikel.idArtikel "
                + "WHERE BestelArtikel.Bezorgd = 0 AND BestelArtikel.BestellingId = "+ bestelId;
        ResultSet result  = MySQLConnection.getMySQLConnection().read(sql);//sql syntax error
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
            System.out.println(ex);
        }
        return list;
    }
    
    private void fillModelBestelId (ResultSet rs, BestelArtikelPojo ba) throws SQLException {
        //bm.getArtikelPojo().setNaam (rs.getString("Naam"));
        ba.setId(rs.getInt("idBestelArtikel"));
        ba.setArtikelId(rs.getInt("ArtikelId"));
        ba.setAantal(rs.getInt("Aantal"));
       // bm.getArtikelPojo().setNaam(rs.getString("Naam"));
        
    }

    @Override
    public boolean updateBestelArtikel(BestelArtikelPojo bArtikel, int regelId) {
                
        String sql = "UPDATE BestelArtikel SET "
                        + "ArtikelId = '" + bArtikel.getArtikelId() + "', "
                        + "Aantal = '" + bArtikel.getAantal() + "'"
                        + "WHERE idBestelArtikel = " + regelId;
                        //+ "Bezorgd = '" +bArtikel.isBezorgd() + "';";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelArtikel(int brId) {
        String sql = "UPDATE BestelArtikel SET Deleted = 1 "
                    + "WHERE Deleted = 0 AND idBestelArtikel = "+brId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    //verwijdering van artikel zonder return value
    @Override
    public void deleteArticleFromOrder (int brId) {
        String sql = "UPDATE BestelArtikel SET Deleted = 1 "
                    + "WHERE Deleted = 0 AND idBestelArtikel = "+brId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean isValidLogin (BestelArtikelPojo bArtikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}