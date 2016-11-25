/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.cheeseapp.adres;

import com.boerpiet.cheeseapp.Connector;
import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public abstract class AdresDAO {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    protected abstract String getUpdateAdresSQL();
    
    protected abstract String getCreateAdresSQL();
    
    protected abstract String getCreateKlantHeeftAdresSQL();
    
    protected abstract String getAdresTypeIdSQL();
    
    protected abstract String getDeleteAdresSQL();
    
    protected abstract String getDeleteKlantHeeftAdresByKlantIdSQL();
    
    protected abstract String getFillKlantModelWithAddressesByIdSQL();
    
    /**
     * Updates adres based on information in AdresPojo.
     * 
     * @param adres the AdresPojo
     * @return boolean, true on success.
     */
    public boolean updateAdres(AdresPojo adres) {
        try (Connection conn = Connector.getConnection();) {
            String sql  = getUpdateAdresSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, adres.getStraat()); 
            pstmt.setInt(2, adres.getHuisnummer());
            pstmt.setString(3, adres.getToevoeging());
            pstmt.setString(4, adres.getPostcode());
            pstmt.setString(5, adres.getWoonplaats());            
            String deleted = adres.isDeleted() ? "1" : "0";
            pstmt.setString(6, deleted);
            pstmt.setInt(7, adres.getIdAdres());
            
            pstmt.executeUpdate();
            return true;

        } catch (Exception ex) {
            logger.error("UpdateAdres failed:" + ex);
            return false;
        }        
    }

    public int createAdres(AdresPojo adres) {
        try (Connection conn = Connector.getConnection()){
            String sql =  getCreateAdresSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, adres.getStraat()); 
            pstmt.setInt(2, adres.getHuisnummer());
            pstmt.setString(3, adres.getToevoeging());
            pstmt.setString(4, adres.getPostcode());
            pstmt.setString(5, adres.getWoonplaats());            

            int rowsAffected = pstmt.executeUpdate();
            
            if(rowsAffected == 0)
                throw new Exception("No adres created.");

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
                else {
                    conn.rollback();
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (Exception ex) {
            logger.error("AdresMySQLDAO create adres failed: " + ex );
            return 0;
        }
    }
    
    public boolean createKlantHeeftAdres(int klantId, int adresId, String adresType) {
        int typeId = getAdresTypeId(adresType);
        if(typeId == 0)
            return false;
        
        try(Connection conn = Connector.getConnection()) {
            String sql = getCreateKlantHeeftAdresSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, klantId); 
            pstmt.setInt(2, adresId);
            pstmt.setInt(3, typeId);

            int insertId =pstmt.executeUpdate();
            
            if(insertId == 0)
                throw new Exception("No adres created.");
            
            return true;            
        } catch (Exception ex) {
            logger.error("createKlantHeeftAdres: " + ex);
            return false;
        }
    }    
    
    public int getAdresTypeId(String type) {
        try(Connection conn = Connector.getConnection()) {
            String sql = getAdresTypeIdSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, type); 
            ResultSet result = pstmt.executeQuery();
            
            result.next();
            int idAdres_type = result.getInt("idAdres_type");

            return idAdres_type;            
        } catch (Exception ex) {
            logger.error("getAdresTypeID failed: " + ex);
            return 0;
        }
    }    
    
    public  boolean deleteAdres(AdresPojo adres) {
        try(Connection conn = Connector.getConnection()) {
        String sql = getDeleteAdresSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, adres.getIdAdres()); 
            int insertId =pstmt.executeUpdate();
            
            if(insertId == 0)
                throw new Exception("Delete adres failed.");
            
            return true;            
        } catch (Exception ex) {
            logger.debug("delete KlantHeeftAdres: " + ex);
            return false;
        }
    }
    
    public boolean deleteKlantHeeftAdresByKlantId(int klantId) {
        try(Connection conn = Connector.getConnection()) {
            String sql = getDeleteKlantHeeftAdresByKlantIdSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, klantId); 
            int insertId =pstmt.executeUpdate();
            
            if(insertId == 0)
                throw new Exception("Delete klant heeft adres failed.");
            
            return true;            
        } catch (Exception ex) {
            logger.error("delete KlantHeeftAdres: " + ex);
            return false;
        }
    }

    public boolean fillKlantModelWithAddressesById(KlantModel skm) {
        try(Connection conn = Connector.getConnection()) {
            String sql = getFillKlantModelWithAddressesByIdSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, skm.getKlantPojo().getId()); 
            
            ResultSet result =  pstmt.executeQuery();
            
            while(result.next()) {
                skm.setAdres(createAdresPojo(result));
            }            

            return true;            
        } catch (Exception ex) {
            logger.error("fillSingleKlantModel: " + ex);
            return false;
        }
    }
    
    private AdresPojo createAdresPojo(ResultSet result) throws SQLException {
        AdresPojo ap = new AdresPojo(result.getInt("idAdres"),
                                     result.getString("Straat"),
                                     result.getInt("Huisnummer"),
                                     result.getString("Toevoeging"),
                                     result.getString("Postcode"),
                                     result.getString("Woonplaats"),
                                     (boolean)result.getBoolean("Deleted"),
                                     result.getString("Soort"));

        return ap;
    }
}