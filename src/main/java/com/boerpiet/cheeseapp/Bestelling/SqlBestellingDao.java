/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestellingPojo;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlBestellingDao extends SuperBestellingDao {

    @Override
    public boolean createBestelling (BestellingPojo bestelling) {
        String sql = "INSERT INTO Bestelling (KlantKey, BestelDatum, AccountKey)"
                + " VALUES ("
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "')";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
            } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public BestellingPojo getBestellingById (int idBestelling) {
        BestellingPojo b = new BestellingPojo ();
        String sql = "SELECT * FROM Bestelling" + " WHERE idBestelling = "+idBestelling;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
        b.setId (rs.getInt(1));
        b.setKlantKey (rs.getInt(2));
        b.setBestelDatum (rs.getDate(3));
        b.setAccountKey (rs.getInt(4));
        b.setDeleted (rs.getBoolean(5));       
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return b;
    }
    
    @Override
    public BestellingPojo getBestellingByKlantId (int klantId) {
        BestellingPojo b = new BestellingPojo ();
        String sql = "SELECT idBestelling FROM Bestelling "+ " WHERE KlantKey = "+klantId;
        try {ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
        if (rs.next()) {
            b.setId (rs.getInt(1));
        }
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return b;
    }

    @Override
    public boolean updateBestelling (BestellingPojo bestelling) {
        String sql = "UPDATE Bestelling SET (idBestelling, KlantKey, BestelDatum, AccountKey, Deleted)"
                + " VALUES ("
                        + "'" + bestelling.getId () + "',"
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "',"
                        + "'" + bestelling.isDeleted () + "')";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelling(BestellingPojo bestelling) {
        String sql = "DELETE * FROM Bestelling" + " WHERE idBestelArtikel = "+bestelling.getId ();
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidLogin(BestellingPojo bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}