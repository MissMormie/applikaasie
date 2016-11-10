/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.Bestelling;

import com.boerpiet.cheeseapp.MySQLConnection;
import com.boerpiet.domeinapp.BestellingModel;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Peaq
 */
public class SqlBestellingDao extends SuperBestellingDao {

    @Override
    public boolean createBestelling (BestellingModel bestelling) {
        String sql = "INSERT INTO Bestelling (id, klantKey, bestelDatum, accountKey, deleted)"
                + " VALUES ("
                        + "'" + bestelling.getId () + "',"
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "',"
                        + "'" + bestelling.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
            } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public BestellingModel getBestelling (int idBestelling) {
        BestellingModel b = new BestellingModel ();
        String sql = "SELECT * FROM Bestelling" + "WHERE idBesteling = "+idBestelling;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().getResult(sql);
        b.setId (rs.getInt(1));
        b.setKlantKey (rs.getInt(2));
        b.setBestelDatum (rs.getDate(3).toLocalDate());
        b.setAccountKey (rs.getInt(4));
        b.setDeleted (rs.getBoolean(5));       
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return b;
    }

    @Override
    public boolean updateBestelling (BestellingModel bestelling) {
        String sql = "UPDATE Artikel SET (id, aantal, bestelId, artikelId, deleted)"
                + " VALUES ("
                        + "'" + bestelling.getId () + "',"
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "',"
                        + "'" + bestelling.isDeleted () + "',";
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelling(BestellingModel bestelling) {
        String sql = "DELETE * FROM Besteling" + "WHERE idBestelArtikel = "+bestelling.getId ();
        try { MySQLConnection.getMySQLConnection().getResult(sql);
        } catch (Exception ex) {
            Logger.getLogger(SqlBestellingDao.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean isValidLogin(BestellingModel bestelling) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}