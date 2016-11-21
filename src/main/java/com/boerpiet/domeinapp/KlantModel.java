/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.adres.AdresDAOFactory;
import com.boerpiet.cheeseapp.klant.KlantDAOFactory;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Sonja
 */

// TODO: Possibly rename Klant Model to klantRepositoryModel? Then change this to Klant 
public class KlantModel {
    // ------------ VARIABLES ---------------------------------

    private KlantPojo klantPojo = new KlantPojo();
    private AdresPojo factuurAdresPojo;
    private AdresPojo postAdresPojo;
    private AdresPojo bezorgAdresPojo;
    private final Logger logger = LoggerFactory.getLogger(KlantModel.class);
    
       
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
    
    /**
     * Initiate filled klantModel 
     * 
     * @param klantPojo         the klant instance {@link KlantPojo}
     * @param factuurAdresPojo  the factuur adress instance {@link AdresPojo}
     * @param postAdresPojo     the post address instance {@code AdresPojo}
     * @param bezorgAdresPojo   the bezorg address instance {@link AdresPojo}
     */
    public KlantModel(KlantPojo klantPojo, AdresPojo factuurAdresPojo, AdresPojo postAdresPojo, AdresPojo bezorgAdresPojo) {
        this.klantPojo = klantPojo;
        this.factuurAdresPojo = factuurAdresPojo;
        this.postAdresPojo = postAdresPojo;
        this.bezorgAdresPojo = bezorgAdresPojo;
    }

    // ------------ Getters and Setters ---------------------------------

    public KlantPojo getKlantPojo() {
        return klantPojo;
    }

    public void setKlantPojo(KlantPojo klantPojo) {
        this.klantPojo = klantPojo;
    }

    public AdresPojo getFactuurAdresPojo() {
        return factuurAdresPojo;
    }

    public void setFactuurAdresPojo(AdresPojo factuurAdresPojo) {
        this.factuurAdresPojo = factuurAdresPojo;
    }

    public AdresPojo getPostAdresPojo() {
        return postAdresPojo;
    }

    public void setPostAdresPojo(AdresPojo postAdresPojo) {
        this.postAdresPojo = postAdresPojo;
    }

    public AdresPojo getBezorgAdresPojo() {
        return bezorgAdresPojo;
    }

    public void setBezorgAdresPojo(AdresPojo bezorgAdresPojo) {
        this.bezorgAdresPojo = bezorgAdresPojo;
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
    public void setAdres(AdresPojo adresPojo) {
        switch(adresPojo.getAdresType().toLowerCase()) {
            case "postadres":
                if(postAdresPojo != null && postAdresPojo.getIdAdres() != 0)
                    adresPojo.setIdAdres(postAdresPojo.getIdAdres());
                this.postAdresPojo = adresPojo;
                break;
            case "factuuradres":
                if(factuurAdresPojo != null && factuurAdresPojo.getIdAdres() != 0)
                    adresPojo.setIdAdres(factuurAdresPojo.getIdAdres());
                this.factuurAdresPojo = adresPojo;
                break;
            case "bezorgadres":
                if(bezorgAdresPojo != null && bezorgAdresPojo.getIdAdres() != 0)
                    adresPojo.setIdAdres(bezorgAdresPojo.getIdAdres());
                this.bezorgAdresPojo = adresPojo;
                break;                
        }
    }
    
    public void setAllAdresses(AdresPojo adresPojo) {
        cloneAdress(adresPojo, "Bezorgadres");
        cloneAdress(adresPojo, "Factuuradres");
        cloneAdress(adresPojo, "Postadres");
    }
    
    public void cloneAdress(AdresPojo adresPojo, String type) {
        try {
            AdresPojo adres = adresPojo.clone();
            adres.setAdresType(type);        
            setAdres(adres);
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
    public int getAdresId(String type) {
        switch(type) {
            case "Postadres":
                if(postAdresPojo != null)
                    return postAdresPojo.getIdAdres();
            case "Factuuradres":
                if(factuurAdresPojo != null)
                    return factuurAdresPojo.getIdAdres();
            case "Bezorgadres":
                if(bezorgAdresPojo != null)
                    return bezorgAdresPojo.getIdAdres();
        }
        return 0;
    }

    /**
     * Deletes complete klant, including addresses
     * 
     * @return boolean true on succesfull delete
     */
    public boolean delete() {
        if(klantPojo.getId() != 0)
            return KlantDAOFactory.getKlantDAO().deleteKlantAndAdressenById(klantPojo.getId());
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
    public boolean updateAdres(String type) {
        switch(type.toLowerCase()) {
            case "postadres":
                if(postAdresPojo != null & postAdresPojo.getIdAdres() != 0) 
                    return AdresDAOFactory.getAdresDAO().updateAdres(postAdresPojo);            
                break;
            case "factuuradres":
                if(factuurAdresPojo != null & factuurAdresPojo.getIdAdres() != 0) 
                    return AdresDAOFactory.getAdresDAO().updateAdres(factuurAdresPojo);            
                break;
            case "bezorgadres":
                if(bezorgAdresPojo != null & bezorgAdresPojo.getIdAdres() != 0) 
                    return AdresDAOFactory.getAdresDAO().updateAdres(bezorgAdresPojo);            
                break;
                
            // All 3 address types are the same
            case "same":
                if(updateAdres("Postadres") && updateAdres("Factuuradres") && updateAdres("Bezorgadres")) 
                    return true;
        }
        return false;
    }
    
    
    // ------------ PRIVATE FUNCTIONS ---------------------------------
    
    private boolean updateKlant() {
        if (klantPojo.getId() == 0)
            return false;
        return KlantDAOFactory.getKlantDAO().updateKlantById(klantPojo);
    }

    @Override
    public String toString() {
        return "KlantModel{" + "klantPojo=" + klantPojo + ", factuurAdresPojo=" + factuurAdresPojo + ", postAdresPojo=" + postAdresPojo + ", bezorgAdresPojo=" + bezorgAdresPojo + ", logger=" + logger + '}';
    }

}
