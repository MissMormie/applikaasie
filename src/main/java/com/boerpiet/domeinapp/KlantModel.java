/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.klant.KlantDAOFactory;

/**
 *
 * @author Sonja
 */

// TODO: Possibly rename Klant Model to klantRepositoryModel? Then change this to Klant 
public class KlantModel {
    private KlantPojo klantPojo;
    private AdresPojo factuurAdresPojo;
    private AdresPojo postAdresPojo;
    private AdresPojo bezorgAdresPojo;
       
    public KlantModel(KlantPojo klantPojo) {
        this.klantPojo = klantPojo;
    }
    
    //used with new klant
    public KlantModel() { }
    
    public KlantModel(KlantPojo klantPojo, AdresPojo factuurAdresPojo, AdresPojo postAdresPojo, AdresPojo bezorgAdresPojo) {
        this.klantPojo = klantPojo;
        this.factuurAdresPojo = factuurAdresPojo;
        this.postAdresPojo = postAdresPojo;
        this.bezorgAdresPojo = bezorgAdresPojo;
    }

    public boolean createKlant(String voornaam, String achternaam, String tussenvoegsel, 
                               String telefoonnummer, String emailadres) {
        
        KlantModel klant = new KlantModel(new KlantPojo(voornaam, achternaam, tussenvoegsel, 
                                        telefoonnummer, emailadres, false));
        return KlantDAOFactory.getKlantDAO().createKlant(klant);
    }

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

    // TODO: Change this to work without KlantPojo, it's already in the model,  
    public boolean updateVoornaam(String in) {
        klantPojo.setVoornaam(in);
        return updateKlant();
    }
    
    public boolean updateTussenvoegsel(String in) {
        klantPojo.setTussenvoegsel(in);
        return updateKlant();
    }

    public boolean updateAchternaam(String in) {
        klantPojo.setAchternaam(in);
        return updateKlant();
    }

    public boolean updateTelefoonnummer(String in) {
        klantPojo.setTelefoonnummer(in);
        return updateKlant();
    }

    public boolean updateEmailadres(String in) {
        klantPojo.setEmailadres(in);
        return updateKlant();
    }
    
    private boolean updateKlant() {
        if (klantPojo.getId() == 0)
            return false;
        return KlantDAOFactory.getKlantDAO().updateKlantById(klantPojo);
    }
    
    // TODO check if this is used otherwise delete. 
    public boolean deleteKlantById(int id) {
        return KlantDAOFactory.getKlantDAO().deleteKlantById(id);
    }

    public void setAdres(AdresPojo adresPojo) {
        switch(adresPojo.getAdresType()) {
            case "Postadres":
                this.postAdresPojo = adresPojo;
                break;
            case "Factuuradres":
                this.factuurAdresPojo = adresPojo;
                break;
            case "Bezorgadres":
                this.bezorgAdresPojo = adresPojo;
                break;
                
            // All 3 address types are the same
            case "Same":
                this.bezorgAdresPojo = adresPojo;
                bezorgAdresPojo.setAdresType("Bezorgadres");
                this.factuurAdresPojo = adresPojo.clone();
                factuurAdresPojo.setAdresType("Factuuradres");
                this.postAdresPojo = adresPojo.clone();
                postAdresPojo.setAdresType("Postadres");
                break;
        }
    }

    public void delete() {
        if(klantPojo.getId() != 0)
            KlantDAOFactory.getKlantDAO().deleteKlantById(klantPojo.getId());
    }
}
