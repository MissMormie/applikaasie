/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantPojo;
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
         
        if(klantModel.createKlant(voornaam, achternaam, tussenvoegsel, telefoonnummer, emailadres)) 
            klantView.showCreationSuccess();
        else {
            klantView.showCreationFail();
            newKlant();
        }
    }


    public void selectKlantToModify() {
        klantView.showKlantList(klantModel.fetchKlantList());
        klantView.showSelectKlantToModify();
        selectKlantToModifyListener();
    }

    public void selectKlantToDelete() {
        klantView.showKlantList(klantModel.fetchKlantList());
        klantView.showSelectKlantToDelete();
        selectKlantToDeleteListener();
    }

    private void selectKlantToModifyListener() {
        String in = input.nextLine();

        if (in.equalsIgnoreCase("n")) 
            return;
        int id = Integer.parseInt(in);
        // validate id
        KlantPojo klant = klantModel.getAccountById(id);
        if (klant == null)
            selectKlantToModify();
        else 
            modifyKlant(klant);        
    }

    private void modifyKlant(KlantPojo klant) {
        klantView.showModifyKlant(klant);
        modifyKlantListener(klant);
    }

    private void modifyKlantListener(KlantPojo klant) {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
        
        int id = Integer.parseInt(in);
        switch(id) {
            case 1: // Voornaam
                klantView.showModifyVoornaam(klant);
                modifyVoornaamListener(klant);
                break;
            case 2: // Tussenvoegsel
                klantView.showModifyTussenvoegsel(klant);
                modifyTussenvoegselListener(klant);
                break;
            case 3: // Achternaam
                klantView.showModifyAchternaam(klant);
                modifyAchternaamListener(klant);
                break;
            case 4: // telefoonnummer
                klantView.showModifyTelefoonnummer(klant);
                modifyTelefoonnummerListener(klant);
                break;
            case 5: 
                klantView.showModifyEmailadres(klant);
                modifyEmailadresListener(klant);
                break;
            default:
                modifyKlant(klant);
                        
    }

}

    private void modifyVoornaamListener(KlantPojo klant) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateVoornaam(klant, in)) { 
            klantView.showUpdateSuccess();
        } else {
            klantView.showUpdateFailed();
            klantView.showModifyVoornaam(klant);
            modifyVoornaamListener(klant);
        }
    }

    private void modifyTussenvoegselListener(KlantPojo klant) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateTussenvoegsel(klant, in)) { 
            klantView.showUpdateSuccess();
        } else {
            klantView.showUpdateFailed();
            klantView.showModifyTussenvoegsel(klant);
            modifyTussenvoegselListener(klant);
        }    
    }

    private void modifyAchternaamListener(KlantPojo klant) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateAchternaam(klant, in)) { 
            klantView.showUpdateSuccess();
        } else {
            klantView.showUpdateFailed();
            klantView.showModifyAchternaam(klant);
            modifyAchternaamListener(klant);
        }    
    }

    private void modifyTelefoonnummerListener(KlantPojo klant) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateTelefoonnummer(klant, in)) { 
            klantView.showUpdateSuccess();
        } else {
            klantView.showUpdateFailed();
            klantView.showModifyTelefoonnummer(klant);
            modifyTelefoonnummerListener(klant);
        }    
    }

    private void modifyEmailadresListener(KlantPojo klant) {
        String in = input.nextLine();

        // check if user wants to go back
        if (in.equalsIgnoreCase("n")) 
            return;
        
        //  Check if update username successful
        if (klantModel.updateEmailadres(klant, in)) { 
            klantView.showUpdateSuccess();
        } else {
            klantView.showUpdateFailed();
            klantView.showModifyEmailadres(klant);
            modifyEmailadresListener(klant);
        }        
    }

    private void selectKlantToDeleteListener() {
        String in = input.nextLine();
        if (in.equalsIgnoreCase("n")) 
            return;
        int id = Integer.parseInt(in);
        if (klantModel.deleteKlantById(id)) {
            klantView.showDeleteKlantSuccess();
        } else {
            klantView.showDeleteKlantFail();
            selectKlantToDelete();
        }
    }
}
