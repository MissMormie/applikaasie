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
import com.boerpiet.domeinapp.Validator;
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
    private BestellingModel bm;
    private BestelArtikelModel bam;
    private ArtikelModel am;
    private BestellingView bv;
    private ArtikelView av;
    private LoginManager lm;
    private ArtikelController ac;
    private BestelArtikelController bac;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    
    public BestellingController (BestellingModel bm, LoginManager lm) {
        this.bm = bm;
        this.lm = lm;
    }
    
    //Klant-opties
    public void startNewOrderKlant () {
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        
        int keuze  = inputIntCheck ();
        
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
        ac = new ArtikelController ();
        
        Date sqlDatum = inputDate();
        
        int artikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
    }
    
    public void modifyOrderKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        bv.startModifyOrder ();
        
        int keuze = inputIntCheck();
        
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
        
        bv = new BestellingView ();
        ac = new ArtikelController ();
        
        bv.showAllOrdersByKlantId(klantId);
        
        int bestelId = inputOrderIdToModify(klantId);
                
        int artikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrderKlant (int klantId) {
        bac = new BestelArtikelController ();
        ac = new ArtikelController ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        int regelId = bac.inputOrderArticleId(bestelId);
        
        int modifiedArtikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptionsKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        
        bv.startDeleteOrder();
        int keuze = inputIntCheck();
        
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
        bv = new BestellingView ();
        bac = new BestelArtikelController ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bac.inputOrderArticleId(bestelId);
        
        bm.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrderKlant (int klantId) {
        bv = new BestellingView ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);
        
        bm.deleteOrder(klantId, bestelId);
    }
    
    //Medewerker-opties    
    public void startNewOrder () {
        
        bv = new BestellingView ();
        
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        int keuze  = inputIntCheck();
        
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
        
        ac = new ArtikelController ();
               
        int klantId = inputKlantId();
        
        Date sqlDatum = inputDate();
                
        int artikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
    }
    
    public void modifyOrder () {
        
        bv = new BestellingView ();
        
        bv.startModifyOrder ();
        
        int keuze = inputIntCheck();
        
        switch(keuze){
            case 1: addArticleToOrder();
                    modifyOrder();
                break;
            case 2: modifyArticleFromOrder();
                    modifyOrder();
                break;
            case 3:
                return;
            default:
                modifyOrder();
                break;
        }        
    }
    
    private void addArticleToOrder () {
        
        bv = new BestellingView ();
        ac = new ArtikelController ();
        
        int klantId = inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);

        int artikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.createArticleToAdd(bestelId, artikelId, aantal);
    }
    
    private void modifyArticleFromOrder () {
        
        bv = new BestellingView ();
        ac = new ArtikelController ();
        bac = new BestelArtikelController ();
        
        int klantId = inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        
        int bestelId = inputOrderIdToModify(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        
        int regelId = bac.inputOrderArticleId(bestelId);
        
        int modifiedArtikelId = ac.inputIntPositiveAndInDatabaseCheck();
        
        int aantal = inputNumberToOrder();
        
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
    }
    
    public void deleteOrderOptions () {
        
        bv = new BestellingView ();
        bv.startDeleteOrder();
        
        int keuze = inputIntCheck();
        
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
        
        bv = new BestellingView ();
        bac = new BestelArtikelController ();
        
        int klantId = inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bac.inputOrderArticleId(bestelId);
        
        bm.deleteOneTupel(klantId, brId, bestelId);           
    }

    private void deleteTotalOrder() {
        int klantId = inputKlantId();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModify(klantId);
        
        bm.deleteOrder(klantId, bestelId);
    }
    
    //input methods
    private Date inputDate () {
        
        bv = new BestellingView ();
        bv.showInputDate();
        Date bestelDatum = inputDateCheck();
        return bestelDatum;
    }
    
    public int inputNumberToOrder () {
        
        bv = new BestellingView ();
        bv.showInputNumberToOrder();
        int aantal = inputIntCheck();
        return aantal;
    }
    
    public int inputOrderIdToModify (int klantId) {
        
        bv = new BestellingView ();
        bv.showOrderIdToModify();
        int bestelId = inputIntPositiveAndInDatabaseCheck (klantId);
        return bestelId;
    }
    
    //validate input methods
    private int inputIntCheck () {
        
        bv = new BestellingView ();
        String intInput = input.nextLine();
        if (Validator.isValidInt(intInput)) {
            return Integer.parseInt(intInput);
        } else {
            bv.showGiveNumber ();
            return inputIntCheck ();
        }
    }
    
    public int inputIntPositiveAndInDatabaseCheck (int klantId) {
        int aId = inputIntCheck();
        if (Validator.isPositiveInt(aId) && bm.checkOrderId(aId, klantId)) {
            return aId;
        } else {
            bv.showGiveNumber();
            return inputIntPositiveAndInDatabaseCheck (klantId);
        }
    }
    
    private Date inputDateCheck () {
        
        bv = new BestellingView ();
        String dateInput = input.nextLine();
        if (Validator.isValidDate(dateInput)) {
            LocalDate bestelDatum = LocalDate.parse(dateInput, format);
            return java.sql.Date.valueOf(bestelDatum);
        } else {
            bv.showGiveDate ();
            return inputDateCheck ();
        }
    }
    
    public int inputKlantId () {
        
        bv = new BestellingView ();
        bv.showInputKlantId();
        int klantId = inputIntCheck ();
        return klantId;
    }
}