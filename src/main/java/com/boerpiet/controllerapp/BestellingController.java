/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.cheeseapp.Bestelling.BestellingDaoFactory;
import com.boerpiet.domeinapp.ArtikelPojo;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingPojo;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestellingView;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestellingController {
    private final Scanner input = new Scanner (System.in);
    private final BestellingPojo bestellingPojo;
    private final BestellingView bestellingView;
    private final BestelArtikelPojo bestelArtikelPojo;
    private final ArtikelView artikelView;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    
    public BestellingController (BestellingPojo bestellingPojo, BestellingView bestellingView,
            BestelArtikelPojo bestelArtikelPojo, ArtikelView artikelView) {
        this.bestellingPojo = bestellingPojo;
        this.bestellingView = bestellingView;
        this.bestelArtikelPojo = bestelArtikelPojo;
        this.artikelView = artikelView;
    }
        
    public void newOrderInput () {
        bestellingView.showNewBestelling();
        makeNewOrder();
        
    }
    private void makeNewOrder () {
        
        String begin = input.nextLine();
        
        if (begin.equalsIgnoreCase("N")) {
            return;
        }
        
        System.out.println("Geef klantid (0 voor medewerkers):");
        int klantId = Integer.parseInt(input.nextLine());
        
        System.out.println("Geef besteldatum (yyyy-mm-dd):");
        LocalDate bestelDatum = LocalDate.parse(input.nextLine(), format);
        Date sqlDatum = java.sql.Date.valueOf(bestelDatum);
        
        System.out.println("Geef accountid:");
        int accountId = Integer.parseInt(input.nextLine());
        
        artikelView.showAllArticles();
        
        System.out.println("Welke kaas wil je bestellen? Voer id in:");
        int artikelId = Integer.parseInt(input.nextLine());
        
        System.out.println("Hoeveel wil je bestellen?");
        int aantal = Integer.parseInt(input.nextLine());

        if (ArtikelDaoFactory.getArtikelDAO("MySQL").findArtikelId(klantId)) {
        bestellingPojo.setKlantKey (klantId);
        bestellingPojo.setBestelDatum (sqlDatum);
        bestellingPojo.setAccountKey (accountId);
        
        int id = BestellingDaoFactory.getBestellingDAO("MySQL").createBestellingWithReturnId(bestellingPojo);
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (id, artikelId, aantal);
        
        BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel);
        bestellingView.showNewBestellingSucces();
        } else {
            bestellingView.showNewBestellingFailure ();
            makeNewOrder();
        }
    }
    public void modifyOrder () {
        
        bestellingView.startModifyOrder ();
        
        int keuze = Integer.parseInt(input.nextLine());
        
        switch(keuze){
            case 1: addArticleToOrder();
                    modifyOrder();
                break;
            case 2: modifyArticleFromOrder();
                    modifyOrder();
                break;
            case 3:
                return;
                //of logout
            default:
                modifyOrder();
                break;
        }        
    }
    
    public void addArticleToOrder () {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef bestelid waar je artikelen aan wilt toe voegen:");
        int bestelId = Integer.parseInt(input.nextLine());
        artikelView.showAllArticles();
        System.out.println("Welk artikel wil je toevoegen? Geef artikelid:");
        int artikelId = Integer.parseInt(input.nextLine());
        System.out.println("Hoeveel wil je bestellen? Geef aantal:");
        int aantal = Integer.parseInt(input.nextLine());
        
        createArticleToAddToOrder (bestelId, artikelId, aantal);

        System.out.println("Dit is de gewijzigde bestelling.");
        bestellingView.showAllBestelRegelsByBestelId(bestelId);        
    }
    
    public void createArticleToAddToOrder (int bestelId, int artikelId, int aantal) {
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (bestelId, artikelId, aantal);
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel)) {
            System.out.println("Artikel is toegevoegd aan bestelling met id: "+bestelId);
            
        }else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            addArticleToOrder();
        }
    }
    
    public void modifyArticleFromOrder () {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef bestelid waar je artikelen wilt wijzigen:");
        int bestelId = Integer.parseInt(input.nextLine());
        bestellingView.showAllBestelRegelsByBestelId(bestelId);
        System.out.println("Geef bestelregelid voor wijziging:");
        int regelId = Integer.parseInt(input.nextLine());
        System.out.println("Geef artikelid voor wijziging:");
        int artikelId = Integer.parseInt(input.nextLine());
        artikelView.showAllArticles();
        System.out.println("Geef artikelid om te bestellen:");
        int modifiedArtikelId = Integer.parseInt(input.nextLine());
        System.out.println("Hoeveel wil je bestellen? Geef aantal:");
        int aantal = Integer.parseInt(input.nextLine());
        modifyArticle (regelId, modifiedArtikelId, aantal);
        
        System.out.println("Dit is de gewijzigde bestelling.");
        bestellingView.showAllBestelRegelsByBestelId(bestelId);
    }
    
    public void modifyArticle (int regelId, int artikelId, int aantal) {
        BestelArtikelPojo ba = new BestelArtikelPojo ();
        //ba.setId(regelId);
        ba.setArtikelId(artikelId);
        ba.setAantal(aantal);
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").updateBestelArtikel(ba, regelId)) {
            System.out.println("Bestelling is nu gewijzigd.");
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw.");
            modifyArticleFromOrder();
        }        
    }
    
    public void deleteOrderOptions () {
        bestellingView.startDeleteOrder();
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1:
                deleteOneTupelFromOrder ();
                break;
            case 2:
                deleteTotalOrder ();
                break;
            case 3:
                return;
            default:
                deleteOrderOptions();
                break;           
        }
    }
    
    public void deleteOneTupelFromOrder () {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef bestelid waar je artikelen wilt verwijderen:");
        int bestelId = Integer.parseInt(input.nextLine());
        bestellingView.showAllBestelRegelsByBestelId(bestelId);        
        System.out.println("Welke regel wil je verwijderen? Geef bestelregelid:");
        int brId = Integer.parseInt(input.nextLine());
        
        if (BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteBestelArtikel(brId)) {
            System.out.println("Artikel is verwijderd van bestelling.");
            System.out.println("Dit is de gewijzigde bestelling.");
            bestellingView.showAllOrdersByKlantId(klantId);
        } else {
            System.out.println("Er is iets misgegaan, probeer het opnieuw");
            return;
        }
        System.out.println("Wil je nog meer artikelen verwijderen van bestelling? (J/N):");
        String jaNee = input.nextLine();
        if (jaNee.equalsIgnoreCase("j")){
            deleteOneTupelFromOrder();
        }
            
    }

    public void deleteTotalOrder() {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef id van bestelling die je wilt verwijderen (bestelid):");
        int bestelId = Integer.parseInt(input.nextLine());
        deleteArticlesFromOrder(bestelId);
        if (BestellingDaoFactory.getBestellingDAO("MySQL").deleteBestelling(bestelId)) {
            System.out.println("Bestelling is verwijderd");
            bestellingView.showAllOrdersByKlantId(klantId);            
        }        
    }
    
    public void deleteArticlesFromOrder (int bestelId) {
        ArrayList<BestelArtikelPojo>baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        for (BestelArtikelPojo ba : baList) {
            BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").deleteArticleFromOrder(bestelId);
            }
        }
}