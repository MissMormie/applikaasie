/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.KlantPojo;
import java.util.List;

/**
 *
 * @author Sonja
 */
public class KlantenView {

    
    private void showDivider() {
        System.out.println("\n------------------------------------------------------------------------");        
    }


    public void showKlantList(List<KlantPojo> klantList) {
        showDivider();
        showKlantListHeader();
        for (int i = 0; i <klantList.size(); i ++) {
            showKlantListItem(klantList.get(i), i+1);
        }
    /*
        for(KlantPojo kp : klantList) {
            showKlantListItem(kp);
        }*/
        System.out.println();
    }

    private void showKlantListHeader() {
        System.out.printf("%-3s %-34s  %-15s %s \n", 
        "id", "naam", "telefoonnummer", "emailadres");
    }

    private void showKlantListItem(KlantPojo kp, int num) {
        String naam;
        if(kp.getTussenvoegsel()==null || kp.getTussenvoegsel().equals(""))
            naam = kp.getVoornaam() + " " + kp.getAchternaam();
        else 
            naam = kp.getVoornaam() + " " + kp.getTussenvoegsel() + " " + kp.getAchternaam();

        System.out.printf("%-3d %-35s %-15s %s \n", 
                num,
                naam,
                kp.getTelefoonnummer(),
                kp.getEmailadres());
    }

    public void showSelectKlantToModify() {
        showDivider();
        System.out.println("Type het id nummer de klant die je wil wijzigen.\n" 
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showSelectKlantToDelete() {
        showDivider();
        System.out.println("Type het id nummer de klant die je wil verwijderen.\n" 
                         + "Of type N om terug te gaan naar het menu.");
    }
    
    public void showSelectKlant() {
        showDivider();
        System.out.println("Type het id nummer van de klant die je wil kiezen.\n" 
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

    public void showSelectKlantfailed() {
        showDivider();
        System.out.println("De gekozen klant bestaat niet.");            
    }
    
    public void showValidNumber() {
        System.out.println("Vul een geldig nummer in.");
    }

}
