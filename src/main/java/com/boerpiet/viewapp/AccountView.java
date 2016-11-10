/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.AccountPojo;
import java.util.ArrayList;

/**
 *
 * @author Sonja
 */
public class AccountView {

    public void showNewAccount() {
        System.out.println("--------------------------------------------------");
        System.out.println("Je gaat nu een nieuw account maken. Wil je dit niet, type dan N en druk op enter.");
        System.out.println("Geef de gegevens voor het nieuwe account als volgt op:");
        System.out.println("Gebruikernaam wachtwoord accounttype klantId, druk hierna op enter.");
        System.out.println("Accounttype is medewerker of klant, klantId is 0 indien er geen klant gekoppeld is.");       
    }

    public void showWrongInput() {
        System.out.println("--------------------------------------------------");
        System.out.println("De gegevens die je hebt ingevoerd kloppen niet.");
        
    }

    public void showNewAccountSuccess() {
        System.out.println("--------------------------------------------------");
        System.out.println("Nieuwe account is succesvol gemaakt");
    }

    public void showNewAccountFailed() {
        System.out.println("--------------------------------------------------");
        System.out.println("Er was een probleem bij het maken van het account, probeer het nog eens");
    }

    public void showAccountList(ArrayList<AccountPojo> accountList) {
        System.out.println("--------------------------------------------------");
        System.out.println("id  gebruikersnaam  accountStatus datum      klant");
        for (AccountPojo ap : accountList) {

            System.out.printf("%-3s %-15s %-13s %s %s \n", ap.getIdAccount(), 
                                                      ap.getGebruikersnaam(), 
                                                      ap.getAccountStatus(),
                                                      ap.getDatum_aanmaak(),
                                                      ap.getKlantId());
        }
        System.out.println();
    }

    public void showDeleteAccount() {
        System.out.println("--------------------------------------------------");
        System.out.println("Type het id nummer van account dat je wil verwijderen.");
        System.out.println("Of type N om terug te gaan naar het menu.");
                
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void showDeleteSure(int id) {
        System.out.println("--------------------------------------------------");
        System.out.println("Weet je zeker dat je het account met " + id + "wil verwijderen?");
        System.out.println("Type J voor ja, N voor nee.");
        
    }

    public void showDeleteAccountSuccess() {
        System.out.println("--------------------------------------------------");
        System.out.println("Account succesvol verwijderd.");
    }

    public void showDeleteAccountFail() {
        System.out.println("--------------------------------------------------");
        System.out.println("Account verwijderen mislukt, probeer het nog eens.");
    }
}
