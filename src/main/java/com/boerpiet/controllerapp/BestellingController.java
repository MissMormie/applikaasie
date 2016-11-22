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
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class BestellingController {
    private final Scanner input = new Scanner (System.in);
    private final BestellingModel bestellingModel;
    private final BestellingView bestellingView;
    private final ArtikelView artikelView;
    
    public BestellingController (BestellingModel bestellingModel,
            BestellingView bestellingView, ArtikelView artikelView) {
        this.bestellingModel = bestellingModel;
        this.bestellingView = bestellingView;
        this.artikelView = artikelView;
    }
    
    //Klant-opties
    public void startNewOrderKlant (int klantId) {

        bestellingView.showNewBestelling();
        int keuze  = Integer.parseInt (input.nextLine());
        
        switch (keuze) {
            case 1:
                makeNewOrderKlant(klantId);
                startNewOrderKlant (klantId);
                break;
            case 2:
                return;
            default:
                startNewOrderKlant (klantId);
                break;
        }        
    }
    
    private void makeNewOrderKlant (int klantId) {
        
        Date sqlDatum = bestellingModel.inputDate();
        
        int accountId = bestellingModel.inputAccountId();
        
        int artikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
        bestellingModel.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
    }
    
    public void modifyOrderKlant (int klantId) {
        
        bestellingView.startModifyOrder ();
        
        int keuze = Integer.parseInt(input.nextLine());
        
        switch(keuze){
            case 1:
                addArticleToOrderKlant(klantId);
                modifyOrderKlant (klantId);
                break;
            case 2:
                modifyArticleFromOrderKlant(klantId);
                modifyOrderKlant (klantId);
                break;
            case 3:
                return;
            default:
                modifyOrderKlant (klantId);
                break;
        }        
    }
    
    private void addArticleToOrderKlant (int klantId) {
        
        bestellingView.showAllOrdersByKlantId(klantId);
        
        int bestelId = bestellingModel.inputOrderIdToModify();
                
        int artikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
        bestellingModel.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrderKlant (int klantId) {
        
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingView.showAllBestelRegelsByBestelId(bestelId);
        int regelId = bestellingModel.inputOrderArticleId();
        
        int modifiedArtikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
        bestellingModel.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptionsKlant (int klantId) {
        
        bestellingView.startDeleteOrder();
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1:
                deleteOneTupelFromOrderKlant (klantId);
                deleteOrderOptionsKlant (klantId);
                break;
            case 2:
                deleteTotalOrderKlant (klantId);
                deleteOrderOptionsKlant (klantId);
                break;
            case 3:
                return;
            default:
                deleteOrderOptionsKlant (klantId);
                break;           
        }
    }
    
    private void deleteOneTupelFromOrderKlant (int klantId) {
                
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingView.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bestellingModel.inputOrderArticleId();
        
        bestellingModel.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrderKlant (int klantId) {
        
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingModel.deleteOrder(klantId, bestelId);
    }
    
    //Medewerker-opties    
    public void startNewOrder () {
        bestellingView.showNewBestelling();
        int keuze  = Integer.parseInt (input.nextLine());
        
        switch (keuze) {
            case 1: makeNewOrder();
                    startNewOrder();
                break;
            case 2:
                return;
            default:
                startNewOrder ();
                break;
        }        
    }
    
    private void makeNewOrder () {
               
        int klantId = bestellingModel.inputKlantId();
        
        Date sqlDatum = bestellingModel.inputDate();
        
        int accountId = bestellingModel.inputAccountId();
        
        int artikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
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
        int klantId = bestellingModel.inputKlantId();
        
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();

        int artikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
        bestellingModel.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrder () {
        int klantId = bestellingModel.inputKlantId();
        
        bestellingView.showAllOrdersByKlantId(klantId);
        
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingView.showAllBestelRegelsByBestelId(bestelId);
        
        int regelId = bestellingModel.inputOrderArticleId();
        
        int modifiedArtikelId = bestellingModel.inputArticleId();
        
        int aantal = bestellingModel.inputNumberToOrder();
        
        bestellingModel.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptions () {
        bestellingView.startDeleteOrder();
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1:
                deleteOneTupelFromOrder ();
                deleteOrderOptions ();
                break;
            case 2:
                deleteTotalOrder ();
                deleteOrderOptions ();
                break;
            case 3:
                return;
            default:
                deleteOrderOptions();
                break;           
        }
    }
    
    private void deleteOneTupelFromOrder () {
        int klantId = bestellingModel.inputKlantId();
        
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingView.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bestellingModel.inputOrderArticleId();
        
        bestellingModel.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrder() {
        int klantId = bestellingModel.inputKlantId();
        
        bestellingView.showAllOrdersByKlantId(klantId);
        int bestelId = bestellingModel.inputOrderIdToModify();
        
        bestellingModel.deleteOrder(klantId, bestelId);
    }
}