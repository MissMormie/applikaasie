/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantPojo;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.KlantView;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class KlantController {
    // ------------ VARIABLES ---------------------------------

    private final KlantModel klantModel; // MVC model
    private final KlantView klantView;   // MVC view
    private final Scanner input = new Scanner(System.in);

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
        String voornaam = notEmptyTextListener();
        if (voornaam.equalsIgnoreCase("n"))
            return; 

        klantModel.getKlantPojo().setVoornaam(voornaam);

        klantView.showTussenvoegsel();
        klantModel.getKlantPojo().setTussenvoegsel(textListener());
        
        klantView.showAchternaam();
        klantModel.getKlantPojo().setAchternaam(notEmptyTextListener());
        
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

    private String textListener() {
        return input.nextLine();
    }    
    
    private String notEmptyTextListener() {
        String text = input.nextLine();
        if(text.isEmpty())
            return textListener();
        return text;        
    }
    
    private int numberListener() {
        String number = input.nextLine();
        if(Validator.isValidInt(number))
            return Integer.parseInt(number);
        
        klantView.showValidNumber();
        return numberListener();
    }    
    
    private String phoneListener() {
        String phone = input.nextLine();
        if(Validator.isValidPhonenumber(phone))
            return phone;
        
        klantView.showTelefoonnummer();
        return phoneListener();
    }
    
    private String emailListener() {
        String email = input.nextLine();
        if (Validator.isValidEmailadres(email))
            return email;
        
        klantView.showEmailAdres();
        return emailListener();
    }
       
    /**
    * Asks if user wants to use the same address voor all addresstypes
 Calls single setAdres if same adress is to be used.
    * Calls setAdres 3 times in succession for different addresses.
    */
    private void setAdresses() {
        klantView.showAskSameAdres();
        String sameAdres = input.nextLine();
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
        klantView.showStraat();
        String straat = notEmptyTextListener();

        klantView.showHuisnummer();
        int huisnummer = numberListener();

        klantView.showToevoeging();
        String toevoeging = textListener();

        klantView.showWoonplaats();
        String woonplaats = notEmptyTextListener(); 
        
        if(type.equalsIgnoreCase("same")) {
            AdresPojo adres = new AdresPojo(klantModel.getAdresId(type), 
                    straat, huisnummer, toevoeging, woonplaats, false);
            klantModel.setAllAdresses(adres);
        } 
        else {
            AdresPojo adres = new AdresPojo(klantModel.getAdresId(type), 
                straat, huisnummer, toevoeging, woonplaats, false, type);
            klantModel.setAdres(adres);
        }
    }

    private void modifyKlantListener() {
        String in = input.nextLine();
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
        String in = input.nextLine();

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
        String in = input.nextLine();

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
        String in = input.nextLine();

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
        String in = input.nextLine();

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
        String in = input.nextLine();

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
        String in = input.nextLine();

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
}
