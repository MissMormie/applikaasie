/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.viewapp.KlantView;
import java.util.Scanner;

/**
 *
 * @author Sonja
 */
public class KlantController {
    private final KlantModel klantModel;
    private final KlantView klantView;
    private final Scanner input = new Scanner(System.in);


    public KlantController(KlantModel klantModel, KlantView klantView) {
        this.klantModel = klantModel;
        this.klantView = klantView;
    }

    public void newKlant() {
        klantView.showNewKlant();
        newKlantListener();
    }
    
    // New klantListener cycles
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
         
        if(klantModel.createKlant(voornaam, achternaam, tussenvoegsel, telefoonnummer, emailadres)) 
            klantView.showCreationSuccess();
        else {
            klantView.showCreationFail();
            newKlant();
        }
    }
    
    public void sameAdres() {
        klantView.showAskSameAdres();
        String sameAdres = input.nextLine();
        if(sameAdres.equalsIgnoreCase("j")) {
            klantView.showSameAdres();
            adresListener("Same");
        } else if (sameAdres.equalsIgnoreCase("n")) {
            klantView.showPostAdres();
            adresListener("PostAdres");
            klantView.showFactuurAdres();
            adresListener("FactuurAdres");
            klantView.showBezorgAdres();
            adresListener("BezorgAdres");
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
        
        AdresPojo adres = new AdresPojo(0, straat, huisnummer, toevoeging, 
                                        woonplaats, false, type);
        klantModel.setAdres(adres);
    }
    

    public void modifyKlant() {
        klantView.showModifyKlant(klantModel);
        modifyKlantListener();
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
            default:
                modifyKlant();
                        
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
    
    public void deleteKlant() {
        klantView.showDeleteSure(klantModel);
        deleteSureListener();
    }
    
    private void deleteSureListener() {
        String in = input.nextLine();

        if (in.equalsIgnoreCase("n")) 
            return;
        if (in.equalsIgnoreCase("j")) {
            klantModel.delete();

            // TODO delete klant en adressen etc.
        } else {
            deleteKlant();
        }
        
    }
}
