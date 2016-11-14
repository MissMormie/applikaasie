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
public class KlantenView {

    
    private void showDivider() {
        System.out.println("\n------------------------------------------------------------------------");        
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
