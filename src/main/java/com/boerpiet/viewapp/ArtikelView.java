/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.ArtikelPojo;

/**
 *
 * @author Peaq
 */
public class ArtikelView {
    
    //show list of articles or just one article
    public void showArticle (int artikelId) {
        ArtikelModel am = new ArtikelModel();
        
        ArtikelPojo ap = am.getArtikel(artikelId);
        showArtikelListHeader ();
        showArticleProperties (ap);  
    }
    
    private void showArticleProperties (ArtikelPojo ap) {

        System.out.printf("%-3s %-25s %-10s %s \n", 
                ap.getId(), 
                ap.getNaam(), 
                ap.getPrijs(),
                ap.getVoorraad());
    }

    public void showAllArticles () {
        ArtikelModel am = new ArtikelModel ();
        
        showDivider();
        showArtikelListHeader();
       
        for (ArtikelPojo ap : am.getAllArticles()) {
            showPropertiesOfArticles (ap);
        }
        System.out.println();
    }
    
    public void showDivider() {
        System.out.println("\n-----------------------------------------------");        
    }

    private void showArtikelListHeader() {
        System.out.printf("%-3s %-25s %-10s %s \n",
                "id",
                "naam",
                "prijs",
                "voorraad");
    }
    
    private void showPropertiesOfArticles (ArtikelPojo ap) {
        //NumberFormat nfPrijs = new DecimalFormat ("#.###,00");
        System.out.printf("%-3s %-25s %-10s %s \n", 
                ap.getId(), 
                ap.getNaam(), 
                ap.getPrijs(),
                ap.getVoorraad());       
    }
    
    //Console messages
    public void startCreateArticle() {
        System.out.println("Wat wil je doen? \n"
                + "1. Nieuw artikel invoeren \n"
                + "2. Terug naar menu \n");
    }
    
    public void articleModifyOptions () {
        System.out.println("Wat wil je wijzigen? \n"
                        + "1. Naam \n"
                        + "2. Prijs \n"
                        + "3. Voorraad \n"
                        + "4. Alledrie \n"
                        + "5. Terug naar menu \n");
    }
    
    public void startDeleteArticle () {
        System.out.println("Wat wil je doen? \n"
                + "1. Artikel verwijderen \n"
                + "2. Terug naar menu \n");
    }
    
    //inputmessages
    public void showInputName () {
        System.out.println("Geef naam voor artikel:");
    }
    public void showInputPrijs () {
        System.out.println("Geef prijs voor artikel:");
    }
    public void showInputVoorraad () {
        System.out.println("Voer voorraad voor dit artikel in:");
    }
    public void showInputArticleIdToAddToOrder () {
        System.out.println("Geef artikelid voor toevoeging:");
    }
    public void showInputArticleIdToModifyInOrder () {
        System.out.println("Geef artikelid om te bestellen in plaats van getoond artikel:");
    }
    public void showInputArticleIdToModify () {
        System.out.println("Geef artikelid voor wijziging:");
    }
    public void showInputArticleIdToDelete () {
        System.out.println("Geef artikelid voor verwijdering:");
    }
    public void showGiveNumber() {
        System.out.println("Geef een geldig nummer of aantal (hele, positieve getallen).");
    }
    public void showGivePrijs() {
        System.out.println("Geef een geldige prijs (max twee decimalen):");
    }
    public void showMenuKeuze() {
        System.out.println("Geef menu-keuze (getal):");
    }
    public void showAddSuccess () {
        System.out.println("Artikel is toegevoegd aan database. \n");
    }
    public void showModifySuccess () {
        System.out.println("Artikel is gewijzigd");
    }
    public void showDeleteSuccess () {
        System.out.println("Artikel is verwijderd uit database.");
    }
    public void showErrorMessage () {
        System.out.println("Er is iets misgegaan, probeer het opnieuw. \n");
    }
    public void showAskSureToDelete() {
        System.out.println("Weet je zeker dat je dit artikel wilt verwijderen? J/N");
    }
    public void showInputArticleId() {
        System.out.println("Geef artikel id:");
    }
    public void showArticleIdNotInDatabase() {
        System.out.println("Artikelid staat niet in database, probeer het opnieuw.");
    }
}