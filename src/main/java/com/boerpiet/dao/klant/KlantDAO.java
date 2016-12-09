/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.klant;

import com.boerpiet.dao.Connector;
import com.boerpiet.dao.account.AccountDAOFactory;
import com.boerpiet.dao.adres.AdresDAO;
import com.boerpiet.dao.adres.AdresDAOFactory;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import java.sql.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public abstract class KlantDAO {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected abstract String getCreateKlantSQL();
    
    protected abstract String getAllKlantenSQL();

    protected abstract String getKlantPojoByIdSQL();
    
    protected abstract String getDeleteKlantByIdSQL();

    protected abstract String getUpdateKlantByIdSQL();
    
    
    /**
     *
     * @param klant KlantModel
     * @return boolean true on successful creation
     */
    public boolean createKlant(KlantModel klant) {
        int klantId = 0;
        try(Connection conn = Connector.getConnection();) {
            String sql = getCreateKlantSQL();
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);
            
            KlantPojo kp = klant.getKlantPojo();
            
            pstmt.setString(1, kp.getVoornaam()); 
            pstmt.setString(2, kp.getAchternaam());
            pstmt.setString(3, kp.getTussenvoegsel());
            pstmt.setString(4, kp.getTelefoonnummer());
            pstmt.setString(5, kp.getEmailadres());
        
            int rowsAffected = pstmt.executeUpdate();
            
            klantId = getCreatedId(pstmt, rowsAffected);
            
            if(klantId == 0) 
                throw new SQLException("klantId == 0");
            
            return createAdressenAndKlantHeeftAdres(klantId, klant);
            
        } catch (SQLException ex) {
            logger.error("KlantDAO.createKlant failed:" + ex);

            // Remove klant from database
            if(klantId != 0)
                deleteKlantAndAdressenById(klantId);
            return false;
        }
    }

    // Returns the created ID.
    private int getCreatedId(PreparedStatement pstmt, int rowsAffected) throws SQLException{
        if(rowsAffected == 0)
            throw new SQLException("No rows created.");

        try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            }
            else {
                throw new SQLException("Creating klant failed, could not obtain ID.");
            }
        }             
    }
    
    
    private boolean createAdressenAndKlantHeeftAdres(int klantId, KlantModel klant) {
                // Create adressen
        AdresDAO ad = AdresDAOFactory.getAdresDAO();
        int bezorgId = ad.createAdres(klant.getBezorgAdresPojo());
        int factuurId = ad.createAdres(klant.getFactuurAdresPojo());
        int postId = ad.createAdres(klant.getPostAdresPojo());

        if ((bezorgId == 0 || factuurId == 0) || postId == 0) {
            return false;
        }
        // Create Tussentabel & Kan alles succesvol worden aangemaakt?               
        return ad.createKlantHeeftAdres(klantId, bezorgId, "Bezorgadres") &&
                ad.createKlantHeeftAdres(klantId, factuurId, "Factuuradres") &&
                ad.createKlantHeeftAdres(klantId, postId, "Postadres");
    }    
   
    /**
     * Fetches a klant list with all current klanten.
     * 
     * @return ArrayList<KlantPojo> list of all klanten
     */
    public ArrayList<KlantPojo> getAllKlanten() {
        ArrayList<KlantPojo> list = new ArrayList<>();

        try (Connection conn = Connector.getConnection();) {
            String sql =  getAllKlantenSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);          
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                KlantPojo kp = new KlantPojo(); 
                fillPojo(rs, kp);
                list.add(kp);
            }
        } catch (Exception ex) {
            logger.error("klantDao.getAllKlanten() :" + ex);
        }
        
        return list;
    }

    /**
     * Fetches the klant data and adress information belonging to that klant
     * based on the klant id.
     * 
     * @param id int - the id of existing klant
     * @return KlantModel the filled KlantModel
     */
    public KlantModel getKlantById(int id) {
        KlantPojo kp = getKlantPojoById(id);
        if(kp == null)
            return null;
        KlantModel km = new KlantModel(kp);
        AdresDAOFactory.getAdresDAO().fillKlantModelWithAddressesById(km);
        return km; 
    }
    
    
    // returns a filled KlantPojo
    private KlantPojo getKlantPojoById(int id) {
        try(Connection conn = Connector.getConnection()) {
            String sql = getKlantPojoByIdSQL();
            
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id); 
            
            ResultSet result =  pstmt.executeQuery();
            
            KlantPojo kp = new KlantPojo(); 
            if(tryFillKlantPojo(result, kp)) 
                return kp;
            else 
                return null;

        } catch (SQLException ex) {
            logger.error("getKlantPojoById: " + ex);
            return null;
        }
    }
    
    /**
     * Updates the klant data from KlantPojo (ie, not the address info) 
     * 
     * @param klant
     * @return boolean, true on success
     */
    public boolean updateKlantById(KlantPojo klant) {
        
        if (klant.getId() == 0)
            return false;
        try(Connection conn = Connector.getConnection();) {

            int deleted = klant.isDeleted() ? 1 : 0;

            String sql = getUpdateKlantByIdSQL(); 
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, klant.getVoornaam()); 
            pstmt.setString(2, klant.getAchternaam());
            pstmt.setString(3, klant.getTussenvoegsel());
            pstmt.setString(4, klant.getTelefoonnummer());
            pstmt.setString(5, klant.getEmailadres());
            pstmt.setInt(6, deleted);
            pstmt.setInt(7, klant.getId());

            pstmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            logger.error("Update klant failed: " + ex);
            return false;
        }
    } 

    /**
     * Deletes the complete Klant information including adres based on klantId
     * Use when only the id of the Klant in known.
     * 
     * @param id int - the id of existing klant
     * @return boolean, true on success
     */
    public boolean deleteKlantAndAdressenById(int id) {
        return deleteKlantAndAdresses(getKlantById(id));
    }
    
    /**
     * Deletes the complete Klant information including adres based on klantModel
     * Use when the klantModel is completely filled.
     * 
     * @param klant the KlantModel of the klant to be deleted.
     * @return boolean, true on success.
     */    
    public boolean deleteKlantAndAdresses(KlantModel klant) {
        AdresDAO ad = AdresDAOFactory.getAdresDAO();

        // Check of de adressen al gevuld zijn in het model, zo niet, doe dit eerst
        if(klant.getBezorgAdresPojo()   == null || klant.getFactuurAdresPojo()  == null ||
           klant.getPostAdresPojo()     == null ) {
            ad.fillKlantModelWithAddressesById(klant);
        }

        // Delete klant Accounts first 
        if(!AccountDAOFactory.getAccountDAO().deleteAccountsByKlantId(klant.getKlantPojo().getId()))
            return false;

        // Delete dan de klant zelf. 
        if (!deleteKlantById(klant.getKlantPojo().getId()))
            return false;
        
        // Delete nu de entries in klant_heeft_adres.
        ad.deleteKlantHeeftAdresByKlantId(klant.getKlantPojo().getId());

        // Delete de adressen. Geen check meer of dit geslaagd is omdat klant toch al weg is.
        ad.deleteAdres(klant.getBezorgAdresPojo()); 
        ad.deleteAdres(klant.getFactuurAdresPojo());
        ad.deleteAdres(klant.getPostAdresPojo());
                
        // Alles succesvol verwijderd.
        return true;
    }
    
    
    private boolean deleteKlantById(int klantId) {
        try(Connection conn = Connector.getConnection()) {
            String sql = getDeleteKlantByIdSQL(); 
            PreparedStatement pstmt = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, klantId); 
            int insertId =pstmt.executeUpdate();
            
            if(insertId == 0)
                throw new Exception("insertID == 0");
            
            return true;            
        } catch (Exception ex) {
            logger.error("delete Klant: " + ex);
            return false;
        }
    }
       
    protected boolean tryFillKlantPojo(ResultSet result, KlantPojo klantPojo) {
        try {
            result.next();
            fillPojo(result, klantPojo);
            return true; 
        } catch (SQLException ex) { 
            return false;
        }
    }
    
    protected void fillPojo(ResultSet result, KlantPojo kp) throws SQLException {
        kp.setId(result.getInt("idKlant"));
        kp.setVoornaam(result.getString("Voornaam"));
        kp.setAchternaam(result.getString("Achternaam"));
        kp.setTussenvoegsel(result.getString("Tussenvoegsel"));
        kp.setTelefoonnummer(result.getString("Telefoonnummer"));
        kp.setEmailadres(result.getString("Emailadres"));
        kp.setDeleted((boolean)result.getBoolean("Deleted"));
    }
}
