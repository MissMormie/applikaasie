/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.utility.Validator;
import com.boerpiet.utility.AdresByPostcode;
import com.boerpiet.utility.ConsoleInput;
import com.boerpiet.viewapp.KlantView;

/**
 *
 * @author Sonja
 */
public class KlantController {
    // ------------ VARIABLES ---------------------------------

    private final KlantModel klantModel; // MVC model
    private final KlantView klantView;   // MVC view

    // ------------ CONSTRUCTORS ---------------------------------

    /**
     * Initiates the KlantController as part of the MVC design pattern
     * 
     * @param klantModel the {@link KlantModel}
     * @param klantView  the {@link KlantView} 
     */
    public KlantController(KlantModel klantModel, KlantView klantView) {
        this.klantModel = klantModel;
        this.klantView = klantView;
    }

    // ------------ PUBLIC FUNCTIONS ---------------------------------

    /**
     * Starts sequence of methods that ends in generating a new Klant
     * unless the user aborts the creation.
     */
    public void newKlant() {
        klantView.showNewKlant();
        String voornaam = ConsoleInput.notEmptyTextInput();
        if (voornaam.equalsIgnoreCase("n"))
            return; 

        klantModel.getKlantPojo().setVoornaam(voornaam);

        klantView.showTussenvoegsel();
        klantModel.getKlantPojo().setTussenvoegsel(ConsoleInput.textInput());
        
        klantView.showAchternaam();
        klantModel.getKlantPojo().setAchternaam(ConsoleInput.notEmptyTextInput());
        
        klantView.showTelefoonnummer();
        klantModel.getKlantPojo().setTelefoonnummer(phoneListener());

        klantView.showEmailAdres();
        klantModel.getKlantPojo().setEmailadres(emailListener());
        
        setAdresses();
                 
        if(klantModel.createKlant()) 
            klantView.showCreationSuccess();
        else {
            klantView.showCreationFail();
            newKlant();
        }        
    }

    /**
     * Starts sequence of methods that ends in deleteing the klant in klantModel
     * unless the user aborts the deletion.
     */    
    public void deleteKlant() {
        klantView.showDeleteSure(klantModel);
        deleteSureListener();
    }

    /**
     * Starts sequence of methods that allows the user to modify attributes of 
     * the klant in klantModel, unless the user aborts.
     */
    public void modifyKlant() {
        klantView.showModifyKlant(klantModel);
        modifyKlantListener();
    }
    
    // ------------ PRIVATE FUNCTIONS ---------------------------------


    
    /**
    * Asks if user wants to use the same address voor all addresstypes
 Calls single setAdres if same adress is to be used.
    * Calls setAdres 3 times in succession for different addresses.
    */
    private void setAdresses() {
        klantView.showAskSameAdres();
        String sameAdres = ConsoleInput.textInput();
        if(sameAdres.equalsIgnoreCase("j")) {
            klantView.showSameAdres();
            setAdres("Same");
        } else if (sameAdres.equalsIgnoreCase("n")) {
            klantView.showPostAdres();
            setAdres("Postadres");
            klantView.showFactuurAdres();
            setAdres("Factuuradres");
            klantView.showBezorgAdres();
            setAdres("Bezorgadres");
        } else {
            setAdresses();
        }
    }
    
    private void setAdres(String type) {
        klantView.showPostcode();
        String postcode = postcodeListener();

        klantView.showHuisnummer();
        int huisnummer = numberListener();

        klantView.showToevoeging();
        String toevoeging = ConsoleInput.textInput();
        
        String[] straatWoonplaats = AdresByPostcode.getAddress(postcode, huisnummer);
        if(straatWoonplaats == null) { 
            klantView.showHuisnummerPostcodeKloptNiet();
            setAdres(type);
            return;
        }
        
        String straat = straatWoonplaats[0];
        String woonplaats = straatWoonplaats[1];
        
        klantView.showAdres(straat, huisnummer, toevoeging, postcode, woonplaats, type);

        if(type.equalsIgnoreCase("same")) {
            AdresPojo adres = new AdresPojo(klantModel.getAdresId(type), 
                    straat, huisnummer, toevoeging, postcode, woonplaats, false);
            klantModel.setAllAdresses(adres);
        } 
        else {
            AdresPojo adres = new AdresPojo(klantModel.getAdresId(type), 
                straat, huisnummer, toevoeging, postcode, woonplaats, false);
            klantModel.setAdres(adres, type);
        }
    }

    private void modifyKlantListener() {
        String in = ConsoleInput.textInput();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        int id = 0;
        if(Validator.isValidInt(in))
            id = Integer.parseInt(in);
        
        switch(id) {
            case 1: // Voornaam
                klantView.showModifyVoornaam(klantModel);
                modifyVoornaamListener();
                break;
            case 2: // Tussenvoegsel
                klantView.showModifyTussenvoegsel(klantModel);
                modifyTussenvoegselListener();
                break;
            case 3: // Achternaam
                klantView.showModifyAchternaam(klantModel);
                modifyAchternaamListener();
                break;
            case 4: // telefoonnummer
                klantView.showModifyTelefoonnummer(klantModel);
                modifyTelefoonnummerListener();
                break;
            case 5: 
                klantView.showModifyEmailadres(klantModel);
                modifyEmailadresListener();
                break;
            case 6: 
                klantView.showPostAdres();
                modifyAdresListener("Postadres");
                break;
            case 7: 
                klantView.showBezorgAdres();
                modifyAdresListener("Bezorgadres");
                break;
            case 8: 
                klantView.showFactuurAdres();
                modifyAdresListener("Factuuradres");
                break;
            case 9: 
                klantView.showSameAdres();
                modifyAdresListener("Same");
                break;
                
            default:
                klantView.showValidNumber();
                modifyKlant();               
        }
    }

    private void modifyAdresListener(String type) {
        setAdres(type);
        if(klantModel.updateAdres(type)) 
            klantView.showUpdateSuccess(klantModel); 
        else {
            klantView.showUpdateFailed(klantModel);
            modifyAdresListener(type);
        }
    }
    
    private void modifyVoornaamListener() {
        String in = ConsoleInput.textInput();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateVoornaam(in)) { 
            klantView.showUpdateSuccess(klantModel);
        } else {
            klantView.showUpdateFailed(klantModel);
            klantView.showModifyVoornaam(klantModel);
            modifyVoornaamListener();
        }
    }

    private void modifyTussenvoegselListener() {
        String in = ConsoleInput.textInput();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateTussenvoegsel(in)) { 
            klantView.showUpdateSuccess(klantModel);
        } else {
            klantView.showUpdateFailed(klantModel);
            klantView.showModifyTussenvoegsel(klantModel);
            modifyTussenvoegselListener();
        }    
    }

    private void modifyAchternaamListener() {
        String in = ConsoleInput.textInput();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateAchternaam(in)) { 
            klantView.showUpdateSuccess(klantModel);
        } else {
            klantView.showUpdateFailed(klantModel);
            klantView.showModifyAchternaam(klantModel);
            modifyAchternaamListener();
        }    
    }

    private void modifyTelefoonnummerListener() {
        String in = ConsoleInput.textInput();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (Validator.isValidPhonenumber(in) && klantModel.updateTelefoonnummer(in)) { 
            klantView.showUpdateSuccess(klantModel);
        } else {
            klantView.showUpdateFailed(klantModel);
            klantView.showModifyTelefoonnummer(klantModel);
            modifyTelefoonnummerListener();
        }    
    }

    private void modifyEmailadresListener() {
        String in = ConsoleInput.textInput();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (Validator.isValidEmailadres(in) && klantModel.updateEmailadres(in)) { 
            klantView.showUpdateSuccess(klantModel);
        } else {
            klantView.showUpdateFailed(klantModel);
            klantView.showModifyEmailadres(klantModel);
            modifyEmailadresListener();
        }
    }
    
    private void deleteSureListener() {
        String in = ConsoleInput.textInput();

        if (in.equalsIgnoreCase("n")) 
            return;
        if (in.equalsIgnoreCase("j")) {
            if(klantModel.delete())
                klantView.showDeleteSuccess();
            else {
                klantView.showDeleteFailed();
            }         
        } else {
            deleteKlant();
        }
    }
    
    private String phoneListener() {
        String phone = ConsoleInput.phoneInput();
        if (phone != null)
            return phone;
        
        klantView.showTelefoonnummer();
        return phoneListener();
    }
    
    private String emailListener() {
        String email = ConsoleInput.emailInput();
        if (email != null)
            return email;
        
        klantView.showEmailAdres();
        return emailListener();
    }
       
    private String postcodeListener() {
        String postcode = ConsoleInput.postcodeInput();
        if(postcode != null && !postcode.isEmpty())
            return postcode;
        
        klantView.showEmailAdres();
        return emailListener();
    }
    
    private Integer numberListener() {
        Integer number = ConsoleInput.numberInput();
        if (number != null)
            return number;
            
        klantView.showValidNumber();
        return numberListener();
    }
}
