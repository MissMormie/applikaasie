/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.BestellingPojo;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestellingView;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestellingController {
    private final Scanner input = new Scanner (System.in);
    private final BestellingModel bestellingModel;
    private final BestellingPojo bestellingPojo;
    private final BestellingView bestellingView;
    private final BestelArtikelPojo bestelArtikelPojo;
    private final ArtikelView artikelView;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    
    public BestellingController (BestellingModel bestellingModel, BestellingPojo bestellingPojo,
            BestellingView bestellingView, BestelArtikelPojo bestelArtikelPojo, ArtikelView artikelView) {
        this.bestellingModel = bestellingModel;
        this.bestellingPojo = bestellingPojo;
        this.bestellingView = bestellingView;
        this.bestelArtikelPojo = bestelArtikelPojo;
        this.artikelView = artikelView;
    }
        
    public void startNewOrder () {
        bestellingView.showNewBestelling();
        int keuze  = Integer.parseInt (input.nextLine());
        
        switch (keuze) {
            case 1: makeNewOrder();
                    startNewOrder();
                break;
            case 2:
                return;
            default: startNewOrder ();
                break;
        }        
    }
    
    private void makeNewOrder () {
               
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
        
        bestellingModel.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
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
    
    private void addArticleToOrder () {
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
        
        bestellingModel.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrder () {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        bestellingView.showAllOrdersByKlantId(klantId);
        
        System.out.println("Geef bestelid waar je artikelen wilt wijzigen:");
        int bestelId = Integer.parseInt(input.nextLine());
        bestellingView.showAllBestelRegelsByBestelId(bestelId);
        
        System.out.println("Geef bestelregelid voor wijziging:");
        int regelId = Integer.parseInt(input.nextLine());
        
        //System.out.println("Geef artikelid voor wijziging:");
        //int artikelId = Integer.parseInt(input.nextLine());
        
        artikelView.showAllArticles();
        System.out.println("Geef artikelid om te bestellen:");
        int modifiedArtikelId = Integer.parseInt(input.nextLine());
        
        System.out.println("Hoeveel wil je bestellen? Geef aantal:");
        int aantal = Integer.parseInt(input.nextLine());
        
        bestellingModel.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
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
    
    private void deleteOneTupelFromOrder () {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef bestelid waar je artikelen wilt verwijderen:");
        int bestelId = Integer.parseInt(input.nextLine());
        
        bestellingView.showAllBestelRegelsByBestelId(bestelId);        
        System.out.println("Welke regel wil je verwijderen? Geef bestelregelid:");
        int brId = Integer.parseInt(input.nextLine());
        
        bestellingModel.deleteOneTupel(klantId, brId, bestelId);
        
        System.out.println("Wil je nog meer artikelen verwijderen van bestelling? (J/N):");
        String jaNee = input.nextLine();
        if (jaNee.equalsIgnoreCase("j")){
            deleteOneTupelFromOrder();
        }
            
    }

    private void deleteTotalOrder() {
        System.out.println("Geef klantid:");
        int klantId = Integer.parseInt(input.nextLine());
        
        bestellingView.showAllOrdersByKlantId(klantId);
        System.out.println("Geef id van bestelling die je wilt verwijderen (bestelid):");
        int bestelId = Integer.parseInt(input.nextLine());
        
        bestellingModel.deleteOrder(klantId, bestelId);
    }
}