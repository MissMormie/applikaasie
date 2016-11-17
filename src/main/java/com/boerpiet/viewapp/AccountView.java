/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.AccountPojo;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Sonja
 */
public class AccountView {

    public void showNewAccount() {
        showDivider();
        System.out.println("Je gaat nu een nieuw account maken. Wil je dit niet, type dan N en druk op enter.\n"
                         + "Geef de gegevens voor het nieuwe account als volgt op:\n"
                         + "Gebruikernaam wachtwoord druk hierna op enter. \n");
    }

    public void showWrongInput() {
        showDivider();
        System.out.println("De gegevens die je hebt ingevoerd kloppen niet.");
        
    }

    public void showNewAccountSuccess() {
        showDivider();
        System.out.println("Nieuwe account is succesvol gemaakt");
    }

    public void showNewAccountFailed() {
        showDivider();
        System.out.println("Er was een probleem bij het maken van het account, probeer het nog eens");
    }

    public void showAccountList(ArrayList<AccountPojo> accountList) {
        showDivider();
        showAccountListHeader();
        for (AccountPojo ap : accountList) {
            showAccountListItem(ap); 
        }
        System.out.println();
    }

    private void showAccountListHeader() {
         System.out.println("id  gebruikersnaam  accountStatus datum      klant");
    }
    
    private void showAccountListItem(AccountPojo ap) {
        System.out.printf("%-3s %-15s %-13s %s-%s-%s %s \n", 
                ap.getIdAccount(), 
                ap.getGebruikersnaam(), 
                ap.getAccountStatus(),
                ap.getDatum_aanmaak().get(Calendar.YEAR),
                ap.getDatum_aanmaak().get(Calendar.MONTH),
                ap.getDatum_aanmaak().get(Calendar.DAY_OF_MONTH),
                ap.getKlantId());       
    }
    
    public void showDeleteAccount() {
        showDivider();
        System.out.println("Type het id nummer van account dat je wil verwijderen.");
        System.out.println("Of type N om terug te gaan naar het menu.");
    }
    
    public void showDeleteSure(int id) {
        showDivider();
        System.out.println("Weet je zeker dat je het account met " + id + "wil verwijderen?");
        System.out.println("Type J voor ja, N voor nee.");
    }

    public void showDeleteAccountSuccess() {
        showDivider();
        System.out.println("Account succesvol verwijderd.");
    }

    public void showDeleteAccountFail() {
        showDivider();
        System.out.println("Account verwijderen mislukt, probeer het nog eens.");
    }

    public void showSelectAccountToModify() {
        showDivider();
        System.out.println("Type het id nummer van account dat je wil wijzigen.\n" 
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showDivider() {
        System.out.println("\n------------------------------------------------------------------------");        
    }
    
    public void showModifyAccount(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account);

        System.out.println("\nWat wil je wijzigen aan dit account?\n"
                         + "1 Gebruikersnaam\n"
                         + "2 Wachtwoord\n"
                         + "3 AccountStatus\n"
                         + "4 klantId\n"
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyUsername(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account);
        System.out.println("\nType de nieuwe gebruikersnaam gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showModifyPassword(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account);
        System.out.println("\nType het nieuwe paswoord gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showModifyAccountStatus(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account);
        System.out.println("\nType de nieuwe accountStatus gevolgd door Enter\n"
                         + "Accounttype is medewerker of klant\n"       
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showModifyKlantId(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account);
        System.out.println("\nType het nieuwe klantId gevolgd door Enter\n"
                         + "KlantID is 0 indien er geen klant bij het account hoort"       
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showUpdateFailed() {
        showDivider();
        System.out.println("Het updaten van dit account is niet goed gegaan, "
                         + "probeer het nog eens");                
    }

    public void showUpdateSuccess() {
        showDivider();
        System.out.println("Update van account is geslaagd.");
    }
}
