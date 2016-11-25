/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.domeinapp.ArtikelPojo;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public class ArtikelView {
    
    public void showArtikelList (ArrayList<ArtikelPojo> artikelList) {
        showDivider();
        showArtikelListHeader();
        for (ArtikelPojo ap : artikelList) {
            showArtikelListItem(ap);
        }
        System.out.println();
    }

    private void showArtikelListHeader() {
        System.out.printf("%-3s %-25s %-10s %s \n",
                "id",
                "naam",
                "prijs",
                "voorraad");
    }
    
    private void showArtikelListItem(ArtikelPojo ap) {
        //NumberFormat nfPrijs = new DecimalFormat ("#.###,00");
        System.out.printf("%-3s %-25s %-10s %s \n", 
                ap.getId(), 
                ap.getNaam(), 
                ap.getPrijs(),
                ap.getVoorraad());       
    }
    
    public void showDivider() {
        System.out.println("\n-----------------------------------------------");        
    }
    public void showAllArticles () {
        ArrayList <ArtikelPojo> aList = ArtikelDaoFactory.getArtikelDAO("MySQL").getAllArticles();
        ArtikelView artList = new ArtikelView();
        artList.showArtikelList(aList);
    }

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
                        + "4. Terug naar menu \n");
    }
    
    public void startDeleteArticle () {
        System.out.println("Wat wil je doen? \n"
                + "1. Artikel verwijderen \n"
                + "2. Terug naar menu \n");
    }
    
    public void showInputName () {
        System.out.println("Geef naam voor artikel:");
    }
    
    public void showInputPrijs () {
        System.out.println("Geef prijs voor artikel:");
    }
    
    public void showInputVoorraad () {
        System.out.println("Geef voorraad van dit artikel:");
    }
    
    public void showInputArticleId () {
        System.out.println("Geef artikelid:");
    }

    public void showGiveNumber() {
        System.out.println("Geef een geldig nummer of aantal (hele, positieve getallen)");
    }

    public void showGivePrijs() {
        System.out.println("Geef een geldige prijs (twee decimalen):");
    }

    public void showMenuKeuze() {
        System.out.println("Geef menu-keuze (getal):");
    }
}