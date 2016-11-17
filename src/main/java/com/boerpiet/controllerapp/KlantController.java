/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantPojo;
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
        newKlantListener();
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

    /** New klantListener cycles through the various attributes of a new klant */
    private void newKlantListener() {
        String voornaam = input.nextLine();
        klantView.showAchternaam();
        String achternaam = input.nextLine();
        klantView.showTussenvoegsel();
        String tussenvoegsel = input.nextLine();
        klantView.showTelefoonnummer();
        String telefoonnummer = input.nextLine();
        klantView.showEmailAdres();
        String emailadres = input.nextLine();
        
        sameAdres();
        
        KlantPojo klantPojo = new KlantPojo(voornaam, achternaam, tussenvoegsel, 
                                        telefoonnummer, emailadres, false);
        klantModel.setKlantPojo(klantPojo);
         
        if(klantModel.createKlant()) 
            klantView.showCreationSuccess();
        else {
            klantView.showCreationFail();
            newKlant();
        }
    }
    
    /**
    * Asks if user wants to use the same address voor all addresstypes
    * Calls single adresListener if same adress is to be used.
    * Calls adresListener 3 times in succession for different addresses.
    */
    private void sameAdres() {
        klantView.showAskSameAdres();
        String sameAdres = input.nextLine();
        if(sameAdres.equalsIgnoreCase("j")) {
            klantView.showSameAdres();
            adresListener("Same");
        } else if (sameAdres.equalsIgnoreCase("n")) {
            klantView.showPostAdres();
            adresListener("Postadres");
            klantView.showFactuurAdres();
            adresListener("Factuuradres");
            klantView.showBezorgAdres();
            adresListener("Bezorgadres");
        } else {
            sameAdres();
        }
    }
    
    
    private void adresListener(String type) {
        klantView.showStraat();
        String straat = input.nextLine();
        klantView.showHuisnummer();
        int huisnummer = Integer.parseInt(input.nextLine());
        klantView.showToevoeging();
        String toevoeging = input.nextLine();
        klantView.showWoonplaats();
        String woonplaats = input.nextLine(); 
        
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
        
        int id = Integer.parseInt(in);
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
                modifyKlant();               
        }
    }

    private void modifyAdresListener(String type) {
        adresListener(type);
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
        if (klantModel.updateTelefoonnummer(in)) { 
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
        if (klantModel.updateEmailadres(in)) { 
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
