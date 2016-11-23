/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.cheeseapp.BestelArtikel.BestelArtikelDaoFactory;
import com.boerpiet.cheeseapp.Bestelling.BestellingDaoFactory;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingPojo;
import java.util.ArrayList;

/**
 *
 * @author Peaq
 */
public class BestellingView {
            
    public void showNewBestelling () {
        
        System.out.println("Wat wil je doen? \n"
                +"1. Nieuwe bestelling invoeren \n"
                +"2. Terug naar het menu \n");
    }
    
    public void showNewBestellingSucces () {
        System.out.println("Nieuwe bestelling is aangemaakt.");
    }
    
    public void showNewBestellingFailure () {
        System.out.println("Er is iets misgegaan, probeer het opnieuw.");
    }

    public void startModifyOrder() {
        System.out.println("Je bent nu in het bestelling-wijzigingsmenu.");
        System.out.println("Wat wil je doen? \n"
                +"1. Artikelen toevoegen aan bestelling \n"
                +"2. Artikelen wijzigen in bestelling \n"
                +"3. Terug naar het menu \n");
        //+ status bestelling veranderen (afgehandeld op true zetten)   later toevoegen?  
    }
    
    public void startDeleteOrder () {
        System.out.println("Dit is het menu voor verwijderen van bestellingen.");
        System.out.println("Wat wil je doen? \n"
                +"1. Een of meerdere artikelen verwijderen van bestelling \n"
                +"2. Gehele bestelling verwijderen \n"
                +"3. Terug naar het menu \n");
    }
    
    public void showBestellingListByKlantId (ArrayList<BestellingPojo>bestelList) {
        showDivider();
        showBestelListHeader();
        for (BestellingPojo bp : bestelList) {
            showBestelListItem(bp);
        }
        System.out.println();
    }
    
    private void showDivider() {
        System.out.println("\n--------------------------------------------------------");        
    }
    
    private void showBestelListHeader() {
        System.out.printf("%-3s %-15s \n",
                "id",
                "besteldatum \n");
    }

    private void showBestelListItem(BestellingPojo bp) {
        System.out.printf("%-3s %-15s \n", 
                bp.getId(),
                bp.getBestelDatum() +"\n");
    }
    public void showAllOrdersByKlantId (int klantId) {
        ArrayList <BestellingPojo> bList = BestellingDaoFactory.getBestellingDAO("MySQL").getAllByKlantId(klantId);
        BestellingView bvList = new BestellingView();
        bvList.showBestellingListByKlantId(bList);
    }
    
    private void showBestelLijstByBestelId (ArrayList<BestelArtikelPojo>bestelList) {
        showDivider();
        showBestelLijstByBestelIdHeader ();
        for (BestelArtikelPojo ba : bestelList) {
            showBestelLijstByBestelIdItem (ba);
        }
        System.out.println ();
        }

    private void showBestelLijstByBestelIdHeader() {
        System.out.printf("%-15s %-15s %-13s \n",
                        "Bestelregelid",
                        "Artikelid",
                        "Aantal besteld \n");                
    }

    private void showBestelLijstByBestelIdItem(BestelArtikelPojo ba) {
        System.out.printf("%-15s %-15s %-13s \n",
                ba.getId(),
                ba.getArtikelId(),
                ba.getAantal()+"\n");
    }
    
    public void showAllBestelRegelsByBestelId (int bestelId) {
        ArrayList <BestelArtikelPojo> baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        BestellingView bvList = new BestellingView();
        bvList.showBestelLijstByBestelId(baList);

    }

    public void showInputDate () {
        System.out.println("Geef besteldatum (yyyy-mm-dd):");
    }
    
    public void showInputNumberToOrder () {
        System.out.println("Hoeveel wil je bestellen? Geef aantal:");
    }
    
    public void showOrderIdToModify () {
        System.out.println("Geef bestelid voor wijziging of verwijdering:");
    }
    
    public void showInputKlantId () {
        System.out.println("Geef klantid:");
    }
}