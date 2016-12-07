/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.AccountPojo;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Sonja
 */
public class AccountView {

    public void showNewAccount() {
        showDivider();
        System.out.println("Je gaat nu een nieuw account maken. Wil je dit niet, type dan N en druk op enter.\n\n"
                         + "Geef de gegevens voor het nieuwe account als volgt op:\n"
                         + "Gebruikernaam <spatie> wachtwoord druk hierna op enter. \n");
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
        for (int i= 0; i < accountList.size(); i ++) {
            showAccountListItem(accountList.get(i), i+1);
        }
        System.out.println();
    }

    private void showAccountListHeader() {
         System.out.println("id  gebruikersnaam  accountStatus datum      klant");
    }
    
    private void showAccountListItem(AccountPojo ap, int num) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(ap.getDatum_aanmaak());
        System.out.printf("%-3s %-15s %-13s %s-%s-%s %3s \n", 
                num, 
                ap.getGebruikersnaam(), 
                ap.getAccountStatus(),                
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH),
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
        System.out.println("\n------------------------------------------------------------------------\n");        
    }
    
    public void showModifyAccount(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account, 1);

        System.out.println("\nWat wil je wijzigen aan dit account?\n"
                         + "1 Gebruikersnaam\n"
                         + "2 Wachtwoord\n"
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyUsername(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account, 1);
        System.out.println("\nType de nieuwe gebruikersnaam gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");        
    }
    
    public void showModifyOwnUsername() {
        showDivider();
        System.out.println("Type uw nieuwe gebruikersnaam gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");
    }

    public void showModifyPassword(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account, 1);
        System.out.println("\nType het nieuwe paswoord gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");        
    }
    
    public void showModifyOwnPassword() {
        showDivider();
        System.out.println("\nType uw nieuwe paswoord gevolgd door Enter\n"
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showModifyAccountStatus(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account, 1);
        System.out.println("\nType de nieuwe accountStatus gevolgd door Enter\n"
                         + "Accounttype is medewerker of klant\n"       
                         + "Of type N om terug te gaan naar het menu.");        
    }

    public void showModifyKlantId(AccountPojo account) {
        showDivider();
        showAccountListHeader();
        showAccountListItem(account, 1);
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

    public void showUserExists() {
        showDivider();
        System.out.println("Account maken mislukt: Deze gebruikersnaam bestaat al.");

    }

    public void showSelectAccountFailed() {
        System.out.println("Het gekozen account bestaat niet. Kies een bestaand account.");            
    }
}
