/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.viewapp;

import com.boerpiet.dao.bestelartikel.BestelArtikelDaoFactory;
import com.boerpiet.dao.bestelling.BestellingDaoFactory;
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
                bp.getBestelDatum());
    }
    public void orderListByKlantId (int klantId) {
        ArrayList <BestellingPojo> bList = BestellingDaoFactory.getBestellingDAO("MySQL").getAllByKlantId(klantId);
        if (bList.isEmpty()) {
            return;
        }
        showBestellingListByKlantId(bList);
    }
    
    public void showBestelLijstByBestelId (ArrayList<BestelArtikelPojo>bestelList) {
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
                ba.getAantal());
    }
    
    public void showAllBestelRegelsByBestelId (int bestelId) {
        ArrayList <BestelArtikelPojo> baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        if (!baList.isEmpty()) {
            BestellingView bvList = new BestellingView();
            showOAIdByOrderId (bestelId);
            bvList.showBestelLijstByBestelId(baList);
        } else {
            showNoOAIdByOrderId(bestelId);
        }
    }
    
    //input messages
    public void showInputDate () {
        System.out.println("Geef besteldatum (yyyy-mm-dd):");
    }
    public void showInputNumberToOrder () {
        System.out.println("Hoeveel wil je bestellen? Geef aantal:");
    }
    public void showOrderIdToModify () {
        System.out.println("Geef bestelid voor wijziging:");
    }
    public void showOrderIdToAddArticle () {
        System.out.println("Geef bestelid om artikelen aan toe te voegen:");
    }
    public void showOrderIdToDelete() {
        System.out.println("Geef bestelid voor verwijdering:");
    }
    public void showInputKlantId () {
        System.out.println("Geef klantid:");
    }
    public void showGiveNumber() {
        System.out.println("Geef een geldig nummer of aantal (hele, positieve getallen)");
    }
    public void showGiveDate() {
        System.out.println("Geef een geldige datum (yyyy-MM-dd):");
    }
    public void showInputOrderId() {
        System.out.println("Geef bestelid:");
    }
    public void showMenuKeuze() {
        System.out.println("Geef menu-keuze (getal):");
    }
    public void showAddOrderSuccess () {
        System.out.println("Bestelling is toegevoegd.");
    }
    public void showModifySuccess () {
        System.out.println("Bestelling is gewijzigd");
    }
    public void showDeleteOrderSuccess () {
        System.out.println("Bestelling is verwijderd uit database.");
    }
    public void showErrorMessage () {
        System.out.println("Er is iets misgegaan, probeer het opnieuw.");
    }
    public void showAskSureToDelete() {
        System.out.println("Weet je zeker dat je dit wilt verwijderen? J/N");
    }
    public void showNoOAIdByOrderId(int bestelId) {
        System.out.println("Er zijn geen bestelregels (meer) bij dit bestelid.");
    }
    
    public void showNoBestellingByKlant() {
        System.out.println("Er zijn geen bestellingen bij deze klant.");
    }
    
    public void showNoOrderIdByKlantId(int klantId) {
        System.out.println("Bestelid hoort niet bij klantid "+klantId);
    }
    public void showEmptyOrderListByKlantId (int klantId) {
        System.out.println("Er zijn geen bestellingen bij klantid "+klantId);
    }   
    public void showOrderListByKlantId(int klantId) {
        System.out.println("Dit zijn de bestellingen bij klantid " + klantId + "\n");
    }
    public void showOAIdByOrderId(int bestelId) {
        System.out.println("Dit zijn de bestelregels van bestelling: "+bestelId);
    }
}