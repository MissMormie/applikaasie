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
    private final BestellingView bestellingView;
    private final BestellingPojo bestellingPojo;
    private final BestelArtikelPojo bestelArtikelPojo;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    
    public BestellingController (BestellingPojo bestellingPojo, BestellingView bestellingView,
            BestelArtikelPojo bestelArtikelPojo) {
        this.bestellingPojo = bestellingPojo;
        this.bestellingView = bestellingView;
        this.bestelArtikelPojo = bestelArtikelPojo;
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
        
        ArrayList <ArtikelPojo> aList = ArtikelDaoFactory.getArtikelDAO("MySQL").getAllArticles();
        ArtikelView artList = new ArtikelView();
        artList.showArtikelList(aList);
        System.out.println("Welke kaas wil je bestellen? Voer id in:");
        int artikelId = Integer.parseInt(input.nextLine());
        
        System.out.println("Hoeveel wil je bestellen?");
        int aantal = Integer.parseInt(input.nextLine());

        if (ArtikelDaoFactory.getArtikelDAO("MySQL").findArtikelId(klantId)) {
        bestellingPojo.setKlantKey (klantId);
        bestellingPojo.setBestelDatum (sqlDatum);
        bestellingPojo.setAccountKey (accountId);
        
        BestellingDaoFactory.getBestellingDAO("MySQL").createBestelling(bestellingPojo);
        
        BestellingPojo bestelling2 = BestellingDaoFactory.getBestellingDAO("MySQL").
                getBestellingByKlantId(klantId);
        
        int bestelId = bestelling2.getId();//krijg niet de laatst ingevoerde idBestelling
                                           //maar de eerst ingevoerde die hoort bij opgegeven klantId
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (bestelId, artikelId, aantal);
        
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
            case 1:
                System.out.println("Geef klantId:");
                int klantId = Integer.parseInt (input.nextLine());
                ArrayList <ArtikelPojo> aList = ArtikelDaoFactory.getArtikelDAO("MySQL").getAllArticles();
                ArtikelView artList = new ArtikelView();
                artList.showArtikelList(aList);
                
                BestellingPojo bestelling2 = BestellingDaoFactory.getBestellingDAO("MySQL").
                getBestellingByKlantId(klantId);
                int bestelId = bestelling2.getId();
                
                BestelArtikelPojo ba1 = 
                        BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").getBestelArtikelByBestelId(bestelId);
                System.out.println("Bestelregelid           aantal\n"+
                                    ba1.getId() +"         " +ba1.getAantal());//moet hier eerst waarden toekennen
                //laat twee lijsten zien, besteld en artikelen
                //input keuze voor wijziging artikelid
                //wijzigen bestelArtikel
                //vragen of aantal ook nog veranderd moet worden, zo ja wijzigen
                //zo nee terug naar hoofdmenu
                break;
            case 2://methode:
                //laat twee lijsten zien, besteld aantal +id, en naam artikel +id
                
                break;
            case 3:
                modifyOrder(); //wil hier andere menu-optie, hoofdmenu bestelling (nieuwe, wijzig, verwijder)
                break;
            default:
                modifyOrder();
                break;
        }
    }
}