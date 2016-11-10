/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.BestelArtikel;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestelArtikelModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlBestelArtikelDao extends SuperBestelArtikelDao {

    @Override
    public boolean createBestelArtikel(BestelArtikelModel bArtikel) {
        String sql = "INSERT INTO Artikel (id, aantal, bestelId, artikelId, deleted)"
                + " VALUES ("
                        + "'" + bArtikel.getId () + "',"
                        + "'" + bArtikel.getAantal () + "',"
                        + "'" + bArtikel.getBestelId () + "',"
                        + "'" + bArtikel.getArtikelId () + "',"
                        + "'" + bArtikel.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
            } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    @Override
    public BestelArtikelModel getBestelArtikel(int bArtikelId) {
        BestelArtikelModel ba = new BestelArtikelModel ();
        String sql = "SELECT * FROM BestelArtikel" + "WHERE idBestelArtikel = "+bArtikelId;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().getResult(sql);
        ba.setId (rs.getInt(1));
        ba.setAantal(rs.getInt(2));
        ba.setBestelId (rs.getInt(3));
        ba.setArtikelId(rs.getInt(4));
        ba.setDeleted (rs.getBoolean(5));       
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return ba;
    }

    @Override
    public boolean updateBestelArtikel(BestelArtikelModel bArtikel) {
        String sql = "UPDATE Artikel SET (id, aantal, bestelId, artikelId, deleted)"
                + " VALUES ("
                        + "'" + bArtikel.getId () + "',"
                        + "'" + bArtikel.getAantal () + "',"
                        + "'" + bArtikel.getBestelId () + "',"
                        + "'" + bArtikel.getArtikelId () + "',"
                        + "'" + bArtikel.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelArtikel(BestelArtikelModel bArtikel) {
        String sql = "DELETE * FROM BestelArtikel" + "WHERE idBestelArtikel = "+bArtikel.getId ();
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestelArtikelDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidLogin (BestelArtikelModel bArtikel) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}