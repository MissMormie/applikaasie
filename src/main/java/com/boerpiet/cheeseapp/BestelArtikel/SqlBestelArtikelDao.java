/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import java.sql.*;
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
        String sql = "SELECT * FROM BestelArtikel" + " WHERE idBestelArtikel = "+bArtikelId;
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
        String sql = "SELECT (ArtikelId, Aantal) " + "WHERE BestellingId = " + bestelId;
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
    public boolean updateBestelArtikel(BestelArtikelPojo bArtikel) {
        String sql = "UPDATE BestelArtikel SET (BestellingId, ArtikelId, aantal)"
                + " VALUES ("
                        //+ "'" + bArtikel.getId () + "'"
                        + "'" + bArtikel.getBestelId () + "',"
                        + "'" + bArtikel.getArtikelId () + "',"
                        + "'" + bArtikel.getAantal () + "');";
                        //+ "'" + bArtikel.isDeleted () + "');";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelArtikel(BestelArtikelPojo bArtikel) {
        String sql = "DELETE * FROM BestelArtikel" + " WHERE idBestelArtikel = "+bArtikel.getId ();
        try { MySQLConnection.getMySQLConnection().createUpdateDelete (sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidLogin (BestelArtikelPojo bArtikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}