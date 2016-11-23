/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.BestelArtikelModel;
import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.LoginManager;
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
    private final BestellingModel bm;
    private final BestelArtikelModel bam;
    private final ArtikelModel am;
    private final BestellingView bv;
    private final ArtikelView av;
    private LoginManager lm;
    
    public BestellingController (BestellingModel bm, BestelArtikelModel bam,
            ArtikelModel am, BestellingView bv, ArtikelView av) {
        this.bm = bm;
        this.bam = bam;
        this.am = am;
        this.bv = bv;
        this.av = av;
    }
    
    //Klant-opties
    public void startNewOrderKlant () {
        int klantId = lm.getAccountPojo().getKlantId();
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        int keuze  = Integer.parseInt (input.nextLine());
        
        switch (keuze) {
            case 1:
                makeNewOrderKlant(klantId, accountId);
                startNewOrderKlant ();
                break;
            case 2:
                return;
            default:
                startNewOrderKlant ();
                break;
        }        
    }
    
    private void makeNewOrderKlant (int klantId, int accountId) {
        
        Date sqlDatum = bm.inputDate();
        
        int artikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
    }
    
    public void modifyOrderKlant () {
        int klantId = lm.getAccountPojo().getKlantId();
        bv.startModifyOrder ();
        
        int keuze = Integer.parseInt(input.nextLine());
        
        switch(keuze){
            case 1:
                addArticleToOrderKlant(klantId);
                modifyOrderKlant ();
                break;
            case 2:
                modifyArticleFromOrderKlant(klantId);
                modifyOrderKlant ();
                break;
            case 3:
                return;
            default:
                modifyOrderKlant ();
                break;
        }        
    }
    
    private void addArticleToOrderKlant (int klantId) {
        
        bv.showAllOrdersByKlantId(klantId);
        
        int bestelId = bm.inputOrderIdToModify();
                
        int artikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrderKlant (int klantId) {
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        int regelId = bam.inputOrderArticleId();
        
        int modifiedArtikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptionsKlant () {
        
        int klantId = lm.getAccountPojo().getKlantId();
        
        bv.startDeleteOrder();
        int keuze = Integer.parseInt(input.nextLine());
        
        switch (keuze) {
            case 1:
                deleteOneTupelFromOrderKlant (klantId);
                deleteOrderOptionsKlant ();
                break;
            case 2:
                deleteTotalOrderKlant (klantId);
                deleteOrderOptionsKlant ();
                break;
            case 3:
                return;
            default:
                deleteOrderOptionsKlant ();
                break;           
        }
    }
    
    private void deleteOneTupelFromOrderKlant (int klantId) {
                
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bam.inputOrderArticleId();
        
        bm.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrderKlant (int klantId) {
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();
        
        bm.deleteOrder(klantId, bestelId);
    }
    
    //Medewerker-opties    
    public void startNewOrder () {
        
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        int keuze  = Integer.parseInt (input.nextLine());
        
        switch (keuze) {
            case 1: makeNewOrder(accountId);
                    startNewOrder();
                break;
            case 2:
                return;
            default:
                startNewOrder ();
                break;
        }        
    }
    
    private void makeNewOrder (int accountId) {
               
        int klantId = bm.inputKlantId();
        
        Date sqlDatum = bm.inputDate();
                
        int artikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
    }
    
    public void modifyOrder () {
        
        bv.startModifyOrder ();
        
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
        
        int klantId = bm.inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();

        int artikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrder () {
        int klantId = bm.inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        
        int bestelId = bm.inputOrderIdToModify();
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        
        int regelId = bam.inputOrderArticleId();
        
        int modifiedArtikelId = am.inputArticleId();
        
        int aantal = bm.inputNumberToOrder();
        
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptions () {
        bv.startDeleteOrder();
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
        int klantId = bm.inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bam.inputOrderArticleId();
        
        bm.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrder() {
        int klantId = bm.inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = bm.inputOrderIdToModify();
        
        bm.deleteOrder(klantId, bestelId);
    }
}