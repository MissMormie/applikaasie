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
        
        int id = BestellingDaoFactory.getBestellingDAO("MySQL").createBestellingWithReturnId(bestellingPojo);
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (id, artikelId, aantal);
        
        BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel);
        bestellingView.showNewBestellingSucces();
        } else {
            bestellingView.showNewBestellingFailure ();
            makeNewOrder();
        }
    }
    
}
    
    