/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.dao.klant;

import com.boerpiet.HibernateUtil;
import com.boerpiet.dao.account.AccountDAOFactory;
import com.boerpiet.domeinapp.KlantHeeftAdresPojo;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.KlantModel;
import java.util.List;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */
public class KlantDAO {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    
    // ----------------------- CREATE --------------------------------------
    
    /**
     * Creates klant and underlying objects, adres and klant_heeft_adres. 
     * @param klant KlantModel
     * @return boolean true on successful creation
     */
    public boolean createKlant(KlantModel klant) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            
            // Save adresses first, address id is necessary to generate klant_heeft_adres.
            for (KlantHeeftAdresPojo khap : klant.getKlantPojo().getAdressen()) {
                session.save(khap.getAdres());
            }

            Integer klantId = (Integer) session.save(klant.getKlantPojo());
            if (klantId == null || klantId == 0)
                return false; // Should never get here, but always throw JDBCException, is this even reachable?
            
            session.getTransaction().commit();
        } catch (JDBCException ex) {
            logger.error("Failed to create klanten: " + ex);
            return false;
        }
        return true;  
    } 

    
    // ----------------------- READ   --------------------------------------
    
    /**
     * Fetches a klant list with all current klanten.
     * 
     * @return ArrayList<KlantPojo> list of all klanten
     */
    public List<KlantPojo> getAllKlanten() {
        
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            List<KlantPojo> klanten = (List<KlantPojo>) session
                    .createQuery("FROM KlantPojo a WHERE a.deleted = :deleted")
                    .setParameter("deleted", false)
                    .list();
            session.getTransaction().commit();
            return klanten;
            
        } catch (JDBCException ex) {
            logger.error("Failed to get list with all klanten: " + ex);
            return null;
        }
    }

    /**
     * Fetches the klant data and adress information belonging to that klant
     * based on the klant id.
     * 
     * @param id int - the id of existing klant
     * @return KlantModel the filled KlantModel
     */
    public KlantPojo getKlantById(int id) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            KlantPojo kp = (KlantPojo) session.get(KlantPojo.class, id);
            session.getTransaction().commit();
            return kp;
            
        } catch (JDBCException ex) {
            logger.error("Failed to get klant by id: " + ex);
            return null;
        }
    }
    
    
    // ----------------------- UPDATE --------------------------------------

    /**
     * Updates the klant data from KlantPojo (ie, not the address info) 
     * 
     * @param klant
     * @return boolean, true on success
     */
    public boolean updateKlantById(KlantPojo klant) {
        try (Session session = HibernateUtil.getSession()) {
            session.beginTransaction();
            session.saveOrUpdate(klant);            
            session.getTransaction().commit();
            return true;
            
        } catch (JDBCException ex) {
            logger.error("Failed to update klant by id: " + ex);
            return false;
        }
    } 

    
    // ----------------------- DELETE --------------------------------------
    
    /**
     * Deletes the complete Klant information including adres based on klantId
     * Use when only the id of the Klant in known.
     * 
     * @param id int - the id of existing klant
     * @return boolean, true on success
     */
    public boolean deleteKlantAndAdressenById(int id) {
        return deleteKlantAndAdressesAndAccounts(getKlantById(id));
    }
    
    /**
     * Deletes the complete Klant information including:
     * - adres 
     * - klant heeft adres.
     * 
     * @param kp KlantPojo
     * @return boolean, true on success.
     */    
    public boolean deleteKlantAndAdressesAndAccounts(KlantPojo kp) {
        // start by deleting accounts:
        if (!AccountDAOFactory.getAccountDAO().deleteAccountsByKlantId(kp.getIdKlant()))
            return false;
        
        // Get klant information, set all tables deleted to true;
        for(KlantHeeftAdresPojo khap : kp.getAdressen()) {
            khap.getAdres().setDeleted(true);
            khap.setDeleted(true);
        }
        kp.setDeleted(true);
        
        // Save deletion
        return updateKlantById(kp);
    }
}
