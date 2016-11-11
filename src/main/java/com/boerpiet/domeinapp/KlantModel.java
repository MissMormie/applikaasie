/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.domeinapp;

import com.boerpiet.cheeseapp.klant.KlantDAOFactory;
import java.util.ArrayList;
/**
 *
 * @author Sonja
 */
public class KlantModel {

    public boolean createKlant(String voornaam, String achternaam, String tussenvoegsel, 
                               String telefoonnummer, String emailadres) {
        
        KlantPojo klant = new KlantPojo(voornaam, achternaam, tussenvoegsel, 
                                        telefoonnummer, emailadres, false);
        
        return KlantDAOFactory.getKlantDAO().createKlant(klant);
    }
    
    public ArrayList<KlantPojo> fetchKlantList() {
        return KlantDAOFactory.getKlantDAO().getAllKlanten();
    }

    public KlantPojo getAccountById(int id) {
        return KlantDAOFactory.getKlantDAO().getKlantById(id);     }

    public boolean updateVoornaam(KlantPojo klant, String in) {
        klant.setVoornaam(in);
        return updateKlantById(klant);
    }
    
    public boolean updateTussenvoegsel(KlantPojo klant, String in) {
        klant.setTussenvoegsel(in);
        return updateKlantById(klant);
    }

    public boolean updateAchternaam(KlantPojo klant, String in) {
        klant.setAchternaam(in);
        return updateKlantById(klant);
    }

    public boolean updateTelefoonnummer(KlantPojo klant, String in) {
        klant.setTelefoonnummer(in);
        return updateKlantById(klant);
    }

    public boolean updateEmailadres(KlantPojo klant, String in) {
        klant.setEmailadres(in);
        return updateKlantById(klant);
    }
    
    private boolean updateKlantById(KlantPojo klant) {
        if (klant.getId() == 0)
            return false;
        return KlantDAOFactory.getKlantDAO().updateKlantById(klant);
    }
    
    public boolean deleteKlantById(int id) {
        return KlantDAOFactory.getKlantDAO().deleteKlantByID(id);
    }


}
