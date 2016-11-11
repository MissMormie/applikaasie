/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.KlantPojo;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public class KlantView {

    public void showNewKlant() {
        showDivider();
        System.out.print("Je gaat nu een nieuwe klant maken. Wil je dit niet, type dan N en druk op enter.\n"
                         + "Je wordt 1 voor 1 om de gegevens gevraagd van de klant:\n"
                         + "Voornaam:\n");
    }

    
    private void showDivider() {
        System.out.println("\n------------------------------------------------------------------------");        
    }

    public void showAchternaam() {
        System.out.println("Achternaam:");
    }

    public void showTussenvoegsel() {
        System.out.println("Tussenvoegsel, druk direct op enter als er geen tussenvoegsel is.");
    }

    public void showTelefoonnummer() {
        System.out.println("Telefoonnummer, druk direct op enter als er geen telefoonnummer is.");
    }

    public void showEmailAdres() {
        System.out.println("Emailadres, druk direct op enter als er geen emailadres is.");
    }

    public void showCreationFail() {
        showDivider();
        System.out.println("Er is iets fout gegaan bij het maken van deze klant, probeer het nogmaals.");
    }

    public void showCreationSuccess() {
        showDivider();
        System.out.println("Klant succesvol aangemaakt.");
    }

    public void showKlantList(ArrayList<KlantPojo> klantList) {
        showDivider();
        showKlantListHeader();
        for(KlantPojo kp : klantList) {
            showKlantListItem(kp);
        }
        System.out.println();
    }

    private void showKlantListHeader() {
        System.out.println("id voornaam tussenvoegsel achternaam telefoonnummer emailadres");
    }

    private void showKlantListItem(KlantPojo kp) {
        System.out.printf("%-3d %-15s %-13s %s %s %s \n", 
                kp.getId(),
                kp.getVoornaam(), 
                kp.getTussenvoegsel(),
                kp.getAchternaam(),
                kp.getTelefoonnummer(),
                kp.getEmailadres());
    }

    public void showSelectKlantToModify() {
        showDivider();
        System.out.println("Type het id nummer de klant die je wil wijzigen.\n" 
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyKlant(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("\nWat wil je wijzigen aan dit account?\n"
                         + "1 Voornaam\n"
                         + "2 Tussenvoegsel\n"
                         + "3 Achternaam\n"
                         + "4 Telefoonnummer\n"
                         + "5 Emailadres\n"
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyVoornaam(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("Type de nieuwe voornaam gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyTussenvoegsel(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("Type de nieuwe voornaam gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyAchternaam(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("Type de nieuwe achternaam gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyTelefoonnummer(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("Type het nieuwe telefoonnummer gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyEmailadres(KlantPojo klant) {
        showDivider();
        showKlantListHeader();
        showKlantListItem(klant);

        System.out.println("Type het nieuwe emailadres gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showUpdateFailed() {
        showDivider();
        System.out.println("Het updaten van deze klant is niet goed gegaan, "
                         + "probeer het nog eens");                
    }

    public void showUpdateSuccess() {
        showDivider();
        System.out.println("Update van deze klant is geslaagd.");
    }

    public void showSelectKlantToDelete() {
        showDivider();
        System.out.println("Type het id nummer de klant die je wil verwijderen.\n" 
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showDeleteKlantSuccess() {
        showDivider();
        System.out.println("Klant succesvol verwijderd.");
    }

    public void showDeleteKlantFail() {
        showDivider();
        System.out.println("Klant verwijderen mislukt, probeer het nog eens.");
    }
    
    
}
