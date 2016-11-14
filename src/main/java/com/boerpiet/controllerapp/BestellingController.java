/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.cheeseapp.Bestelling.BestellingDaoFactory;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingPojo;
import com.boerpiet.viewapp.BestellingView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestellingController {
    private final Scanner input = new Scanner (System.in);
    private final BestellingView bestellingView;
    private final BestellingPojo bestellingModel;
    private final BestelArtikelPojo bestelArtikelModel;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("dd-MM-yyyy");
    
    public BestellingController (BestellingPojo bestellingModel, BestellingView bestellingView,
            BestelArtikelPojo bestelArtikelModel) {
        this.bestellingModel = bestellingModel;
        this.bestellingView = bestellingView;
        this.bestelArtikelModel = bestelArtikelModel;
    }
    
    public void newOrderInput () {
        bestellingView.showNewBestelling();
        makeNewOrder();
        
    }
    public void makeNewOrder () {
        //DateTimeFormatter format = DateTimeFormatter.ofPattern ("dd-MM-yyyy");
        String begin = input.nextLine();
        
        if (begin.compareToIgnoreCase("N")==0) {
            return;
        }
        
        System.out.println("Geef klantid (0 voor medewerkers):");
        int klantId = Integer.parseInt(input.nextLine());
        System.out.println("Geef besteldatum (dd-mm-yyyy):");
        LocalDate bestelDatum = LocalDate.parse(input.nextLine(), format);
        System.out.println("Geef accountid:");
        int accountId = Integer.parseInt(input.nextLine());
        System.out.println (ArtikelDaoFactory.getArtikelDAO("MySQL").getAllArticles());
        System.out.println("Welke kaas wil je bestellen? Voer id in:");
        int artikelId = Integer.parseInt(input.nextLine());
        System.out.println("Hoeveel wil je bestellen?");
        int aantal = Integer.parseInt(input.nextLine());
        //if idArtikel is in database maak nieuwe bestelling met klantId, besteldatum en accountkey
        if (ArtikelDaoFactory.getArtikelDAO("MySQL").findArtikelId(klantId)) {
        bestellingModel.setKlantKey (klantId);
        bestellingModel.setBestelDatum (bestelDatum);
        bestellingModel.setAccountKey (accountId);

        //BestellingPojo bestelling = new BestellingPojo (klantId, bestelDatum, accountId);
        
        BestellingDaoFactory.getBestellingDAO("MySQL").createBestelling(bestellingModel);
        
        //assign bestelling to object besteling2 to get bestelId for BestelArtikel
        BestellingPojo bestelling2 = new BestellingPojo ();
        bestelling2 = BestellingDaoFactory.getBestellingDAO("MySQL").
                getBestellingByKlantId(klantId);
        
        BestelArtikelPojo bestelregel = new BestelArtikelPojo (artikelId, bestelling2.getId(), aantal);
        
        BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").createBestelArtikel(bestelregel);
        bestellingView.showNewBestellingSucces();
        } else {
            bestellingView.showNewBestellingFailure ();
            makeNewOrder();
        }
    }
}