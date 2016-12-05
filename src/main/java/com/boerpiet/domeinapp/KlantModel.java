
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.dao.adres.AdresDAOFactory;
import com.boerpiet.dao.klant.KlantDAOFactory;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */

public class KlantModel {
    // ------------ VARIABLES ---------------------------------

    private KlantPojo klantPojo = new KlantPojo();
    private final Logger logger = LoggerFactory.getLogger(KlantModel.class);
    /*
    private AdresPojo bezorgAdresPojo;
    private AdresPojo factuurAdresPojo;
    private AdresPojo postAdresPojo;
    */
       
    // ------------ CONSTRUCTORS ---------------------------------
    
    
    /** 
     * Initiate klantmodel with a specific {@link klantPojo} 
     * 
     * @param klantPojo the klant instance {@code Klantpojo}
     */
    public KlantModel(KlantPojo klantPojo) {
        this.klantPojo = klantPojo;
    }
    
    
    /** 
     * Initiate empty klantmodel, might be used with new klant 
     */
    public KlantModel() { 
    }
  
    // ------------ Getters and Setters ---------------------------------

    public KlantPojo getKlantPojo() {
        return klantPojo;
    }

    public void setKlantPojo(KlantPojo klantPojo) {
        this.klantPojo = klantPojo;
    }

    public AdresPojo getFactuurAdresPojo() {
        return getAdresPojo("Factuuradres");
    }

    public void setFactuurAdresPojo(AdresPojo factuurAdresPojo) {
        setAdres(factuurAdresPojo, "Factuuradres");
    }

    public AdresPojo getPostAdresPojo() {
        return getAdresPojo("Postadres");
    }
    
    public void setPostAdresPojo(AdresPojo postAdresPojo) {
        setAdres(postAdresPojo, "Postadres");
    }

    public AdresPojo getBezorgAdresPojo() {
        return getAdresPojo("Postadres");
    }
    
    public void setBezorgAdresPojo(AdresPojo bezorgAdresPojo) {
        setAdres(bezorgAdresPojo, "Bezorgadres");
    }

    // ------------ PUBLIC FUNCTIONS ---------------------------------

    /**
     * Updates klant in the data storage with new voornaam.
     * 
     * @param in the first name input
     * @return boolean true on succes
     */
    public boolean updateVoornaam(String in) {
        klantPojo.setVoornaam(in);
        return updateKlant();
    }

    /** 
     * Updates klant in the data storage with new Tussenvoegsel.
     *
     * @param in the tussenvoegsel
     * @return boolean true on succes
     */   
    public boolean updateTussenvoegsel(String in) {
        klantPojo.setTussenvoegsel(in);
        return updateKlant();
    }

    /** 
     * Updates klant in the data storage with new achternaam.
     *
     * @param in the achternaam
     * @return boolean true on succes
     */   
    public boolean updateAchternaam(String in) {
        klantPojo.setAchternaam(in);
        return updateKlant();
    }

    /** 
     * Updates klant in the data storage with new Telefoonnummer.
     *
     * @param in the telefoonnummer
     * @return boolean true on succes
     */     
    public boolean updateTelefoonnummer(String in) {
        klantPojo.setTelefoonnummer(in);
        return updateKlant();
    }

    /** 
     * Updates klant in the data storage with new emailadres.
     *
     * @param in the emailadres
     * @return boolean true on succes
     */   
    public boolean updateEmailadres(String in) {
        klantPojo.setEmailadres(in);
        return updateKlant();
    }
       
    /** 
     * Creates this customer in data storage
     *
     * @return boolean true on success;
     */   
    public boolean createKlant() {
        if (KlantDAOFactory.getKlantDAO().createKlant(this)) {
            logger.info("new klant created: " + this.toString());
            return true;
        }
        return false;
    }    
    
    /**
     * Set the type of adres that is in the {@link AdresPojo}
     * if AdresPojo.adresType = "Same" the adresPojo overwrites all 
     * adrestypes. ie. all klant addresses are now clones of adresPojo.
     * 
     * @param adresPojo 
     */
    public void setAdres(AdresPojo adresPojo, String adresType) {
        // Check if adrestype already exists, if so replace, but copy id.
        for (KlantHeeftAdresPojo khAdres : klantPojo.getAdressen()) {
            if(khAdres.getAdres_type().getSoort().equals(adresType)) {
                adresPojo.setIdAdres(khAdres.getAdres().getIdAdres());
                khAdres.setAdres(adresPojo);
                return;
            }
        }
        
        // if adrestype doesn't exist create one.
        KlantHeeftAdresPojo khap = new KlantHeeftAdresPojo();
        khap.setKlant(klantPojo);
        khap.setAdres(adresPojo);
        khap.setAdres_type(getAdresTypePojo(adresType));
        
        // Add klant HeeftAdresPojo to klant Pojo.
        klantPojo.getAdressen().add(khap);
    }
    
    public void setAllAdresses(AdresPojo adresPojo) {
        cloneAndSetAdress(adresPojo, "Bezorgadres");
        cloneAndSetAdress(adresPojo, "Factuuradres");
        cloneAndSetAdress(adresPojo, "Postadres");
    }
    
    public void cloneAndSetAdress(AdresPojo adresPojo, String adresType) {
        try {
            AdresPojo adres = adresPojo.clone();
            setAdres(adres, adresType);
        } catch (CloneNotSupportedException ex) {
            logger.debug("failed to clone adres: " + adresPojo.toString());
        }
    }
    
    /**
     * Gets the id for the type of adres
     * 
     * @param type String (Postadres, Factuuradres, Bezorgadres)
     * @return int the adresid
     */    
    public int getAdresId(String adresType) {
        for (KlantHeeftAdresPojo adres : klantPojo.getAdressen()) {
            if(adres.getAdres_type().getSoort().equals(adresType)) {
                return adres.getAdres().getIdAdres();
            }
        }
        return 0;
    }

    /**
     * Deletes complete klant, including addresses
     * 
     * @return boolean true on succesfull delete
     */
    public boolean delete() {
        if(klantPojo.getIdKlant() != 0)
            return KlantDAOFactory.getKlantDAO().deleteKlantAndAdressesAndAccounts(klantPojo);
        return false;
    }

    /**
     * Updates the type of adres in external data storage assuming the 
     * adres is in the model not null and it has an id.
     * 
     * @param type the type of address (Postadres, Factuuradres, Bezorgadres).
     * @return boolean true on succesfull update
     * 
     */
    public boolean updateAdres(String adresType) {
        boolean success = false;
        for (KlantHeeftAdresPojo adres : klantPojo.getAdressen()) {
            if(adres.getAdres_type().getSoort().equals(adresType) || adresType.equalsIgnoreCase("same")) {
                success = AdresDAOFactory.getAdresDAO().updateAdres(adres.getAdres());
            }
        }
        return success;
    }
    
    
    // ------------ PRIVATE FUNCTIONS ---------------------------------
    
    private boolean updateKlant() {
        if (klantPojo.getIdKlant() == 0)
            return false;
        return KlantDAOFactory.getKlantDAO().updateKlantById(klantPojo);
    }

    @Override
    public String toString() {
        return "KlantModel{" + "klantPojo=" + klantPojo + "}";
    }
    
    private AdresPojo getAdresPojo(String adresType) {
        for (KlantHeeftAdresPojo adres : klantPojo.getAdressen()) {
            if(adres.getAdres_type().getSoort().equals(adresType))
                return adres.getAdres();
        }
        return null;
    }

    // TODO get this from the database instead.
    private AdresTypePojo getAdresTypePojo(String adresType) {
        AdresTypePojo atp = new AdresTypePojo();
        atp.setSoort(adresType);
        if(adresType.equals("Bezorgadres"))
            atp.setIdAdres_type(3);
        if(adresType.equals("Factuuradres"))
            atp.setIdAdres_type(2);
        if(adresType.equals("Postadres"))
            atp.setIdAdres_type(1);
        return atp;
    }
}
