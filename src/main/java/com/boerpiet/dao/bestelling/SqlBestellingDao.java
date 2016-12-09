/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.bestelling;

import com.boerpiet.dao.MySQLConnection;
import com.boerpiet.domeinapp.BestellingPojo;
import java.sql.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Peaq
 */
public class SqlBestellingDao extends SuperBestellingDao {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean createBestelling (BestellingPojo bestelling) {
        String sql = "INSERT INTO Bestelling (KlantKey, BestelDatum, AccountKey)"
                + " VALUES ("
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "')";
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
            } catch (Exception ex) {
                logger.error ("Aanmaken van nieuwe bestelling is mislukt "+ ex);
            return false;
        }
        return true;
    }
    
    @Override
    public int createBestellingWithReturnId (BestellingPojo bestelling) {
        String sql = "INSERT INTO Bestelling (KlantKey, BestelDatum, AccountKey)"
                + " VALUES ("
                        + "'" + bestelling.getKlantKey () + "',"
                        + "'" + bestelling.getBestelDatum() + "',"
                        + "'" + bestelling.getAccountKey() + "')";
        try { int key = MySQLConnection.getMySQLConnection().createAndReturnID(sql);
              return key;
            } catch (Exception ex) {
                logger.error ("Aanmaken van nieuwe bestelling is mislukt "+ ex);
            return 0;
        }
    }    

    @Override
    public BestellingPojo getBestellingById (int idBestelling) {
        BestellingPojo b = new BestellingPojo ();
        String sql = "SELECT * FROM Bestelling" + " WHERE Deleted = 0 AND "
                + "Afgehandeld = 0 AND idBestelling = "+idBestelling;
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
        b.setId (rs.getInt(1));
        b.setKlantKey (rs.getInt(2));
        b.setBestelDatum (rs.getDate(3));
        b.setAccountKey (rs.getInt(4));
        } catch (Exception ex) {
            logger.warn("Geen bestelling gevonden met bestelid " +ex +sql);
            return null;
        }
        return b;
    }

    @Override
    public BestellingPojo getBestellingByKlantId (int klantId) {
        BestellingPojo b = new BestellingPojo ();
        String sql = "SELECT idBestelling FROM Bestelling "
                + "WHERE Deleted = 0 AND Afgehandeld = 0 AND KlantKey = "+klantId;
        try {ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
        if (rs.next()) {
            b.setId (rs.getInt(1));
        }
        } catch (Exception ex) {
            logger.warn ("Geen bestelling(en) gevonden bij dit klantid: "+ex +sql);
            return null;
        }
        return b;
    }
    
    @Override
    public boolean findBestellingId (int bestelId, int klantId) {
        String sql = "SELECT idBestelling FROM Bestelling WHERE Deleted = 0 "
                + " AND idBestelling = "+bestelId
                + " AND KlantKey = "+klantId+";";
        try {
            ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            return rs != null;
        } catch (Exception ex) {
            logger.error ("Geen bestelling gevonden met dit id: "+ex +sql);
                return false;
        }
    }
    
    @Override
    public int getMaxBestellingId () {
        String sql = "SELECT idBestelling FROM Bestelling WHERE idBestelling  = "
                + "(SELECT MAX(idBestelling) FROM Bestelling)";
        try { ResultSet rs = MySQLConnection.getMySQLConnection().read(sql);
            int max  = 0;
            if (rs.next()) {
                max = rs.getInt(1);
            } return max;
        } catch (Exception ex) {
            logger.warn ("Bestelid is niet in database "+ ex);
            return 0;
        }
    }
    
    //wordt momenteel niet gebruikt, wijzigingen in bestelling worden gedaan met BestelArtikel
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
            logger.error ("Wijziging is mislukt: "+ex);
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteBestelling(int bestelId) {
        String sql = "UPDATE Bestelling SET Deleted = 1, Afgehandeld = 1"
                + " WHERE idBestelling = " + bestelId;
        try { MySQLConnection.getMySQLConnection().createUpdateDelete(sql);
        } catch (Exception ex) {
            logger.error ("Verwijderen van bestelling is mislukt: "+ex);
            return false;
        }
        return true;
    }
    
    @Override
    public ArrayList <BestellingPojo> getAllByKlantId (int klantId) {
        String sql = "SELECT idBestelling, BestelDatum FROM Bestelling "
                   + "WHERE Deleted = 0 AND Afgehandeld = 0 AND KlantKey = "+klantId;
            
        ArrayList<BestellingPojo> list = new ArrayList<>();
       
        try {
            ResultSet result = MySQLConnection.getMySQLConnection().read(sql);
            if (result == null) {
            System.out.println("Geen bestelling gevonden voor dit klantid.");
            }            
            while (result.next()) {
            BestellingPojo bp = new BestellingPojo();
            fillPojoKlantId (result, bp);
            list.add(bp);
            if (list.isEmpty()) {
                return null;
            }
        }
    }   catch (SQLException ex) {
            logger.error("Kon geen bestellingslijst maken: "+ex);
        }
        return list;       
    }

    private void fillPojoKlantId (ResultSet result, BestellingPojo bp) throws SQLException {
        bp.setId(result.getInt("idBestelling"));
        bp.setBestelDatum(result.getDate("BestelDatum"));
    }
}