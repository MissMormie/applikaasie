/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.AdresPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantPojo;

/**
 *
 * @author Sonja
 */
public class KlantView {
    private void showDivider() {
        System.out.println("\n------------------------------------------------------------------------");        
    }
    
    public void showNewKlant() {
        showDivider();
        System.out.print("Je gaat nu een nieuwe klant maken. Wil je dit niet, type dan N en druk op enter.\n"
                         + "Je wordt 1 voor 1 om de gegevens gevraagd van de klant:\n"
                         + "Voornaam:\n");
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
    
    public void showAskSameAdres() {
        System.out.println("Zijn het post, factuur en bezorgadres gelijk? J/N");
    }

    public void showCreationFail() {
        showDivider();
        System.out.println("Er is iets fout gegaan bij het maken van deze klant, probeer het nogmaals.");
    }

    public void showCreationSuccess() {
        showDivider();
        System.out.println("Klant succesvol aangemaakt.");
    }
    

    public void showModifyKlant(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("\nWat wil je wijzigen aan dit account?\n"
                         + "1 Voornaam\n"
                         + "2 Tussenvoegsel\n"
                         + "3 Achternaam\n"
                         + "4 Telefoonnummer\n"
                         + "5 Emailadres\n"
                         + "6 PostAdres\n"
                         + "7 BezorgAdres\n"
                         + "8 FactuurAdres\n"
                         + "9 Alle adressen\n"
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyVoornaam(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("Type de nieuwe voornaam gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyTussenvoegsel(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("Type het nieuwe tussenvoegsel gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyAchternaam(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("Type de nieuwe achternaam gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyTelefoonnummer(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("Type het nieuwe telefoonnummer gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showModifyEmailadres(KlantModel klant) {
        showDivider();
        showKlant(klant);

        System.out.println("Type het nieuwe emailadres gevolgd door Enter \n"
                         + "of type N om terug te gaan naar het menu");
    }

    public void showUpdateFailed(KlantModel klant) {
        showDivider();
        showKlant(klant);
        System.out.println("Het updaten is niet goed gegaan, "
                         + "probeer het nog eens");                
    }

    public void showUpdateSuccess(KlantModel klant) {
        showDivider();
        showKlant(klant);
        System.out.println("Update is geslaagd.");
    }

    public void showDeleteSure(KlantModel klant) {
        showDivider();
        showKlant(klant);
        System.out.println("Weet je zeker dat je deze klant wilt verwijderen?\n"
                + "Type J voor ja of N voor nee.");
    }
    
    private void showKlant(KlantModel klant) {
        
        System.out.println(klant.getKlantPojo().getId() + " " + klant.getKlantPojo().getVoornaam() + " " +  
                klant.getKlantPojo().getTussenvoegsel() + " " + klant.getKlantPojo().getAchternaam() + " " +
                klant.getKlantPojo().getTelefoonnummer() + " " + klant.getKlantPojo().getEmailadres());
        showAdres(klant.getBezorgAdresPojo());
        showAdres(klant.getFactuurAdresPojo());
        showAdres(klant.getPostAdresPojo());
    }
    
    private void showAdres(AdresPojo adres) {
        System.out.println( adres.getAdresType() + ": " + adres.getStraat() + " " + adres.getHuisnummer() +
                            adres.getToevoeging() + " " + adres.getWoonplaats());
    }
    
    public void showPostAdres() {
        showDivider();
        System.out.println("Geef nu het postadres op:");
    }
    
    public void showFactuurAdres() {
        showDivider();
        System.out.println("Geef nu het factuuradres op:");
    }
    
    public void showBezorgAdres() {
        showDivider();
        System.out.println("Geef nu het bezorgadres op:");
    }
    
    public void showSameAdres() {
        showDivider();
        System.out.println("Geef nu het adres op:");
    }    

    public void showStraat() {
        System.out.println("Straatnaam:");
    }

    public void showHuisnummer() {
        System.out.println("Huisnummer:");
    }

    public void showToevoeging() {
        System.out.println("Toevoeging, druk direct op enter als er geen tussenvoegsel is.");
    }

    public void showWoonplaats() {
        System.out.println("Woonplaats");
    }

    public void showDeleteSuccess() {
        showDivider();
        System.out.println("Klant succesvol verwijderd.");
    }

    public void showDeleteFailed() {
        showDivider();
        System.out.println("Klant verwijderen mislukt, probeer het nogmaal.\n "
                + "Blijft het probleem zich voordoen neem contact op met IT.");
    }
}
