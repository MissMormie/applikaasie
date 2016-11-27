/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.LoginManager;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestelArtikelView;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.KlantenView;
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
    private BestelArtikelView bav;
    private BestellingView bv;
    private ArtikelView av;
    private final LoginManager lm;
    private ArtikelController ac;
    private BestelArtikelController bac;
    private final DateTimeFormatter format = DateTimeFormatter.ofPattern ("yyyy-MM-dd");
    
    public BestellingController (BestellingModel bm, LoginManager lm) {
        this.bm = bm;
        this.lm = lm;
    }
    
    //Klant-opties
    public void startNewOrderByKlant () {
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine();
        int keuze = inputIntCheck (intKeuze);
        
        switch (keuze) {
            case 1:
                makeNewOrderByKlant(klantId, accountId);
                startNewOrderByKlant ();
                break;
            case 2:
                return;
            default:
                startNewOrderByKlant ();
                break;
        }        
    }
    
    private void makeNewOrderByKlant (int klantId, int accountId) {
        ac = new ArtikelController ();
        av = new ArtikelView ();
        
        Date sqlDatum = inputDateCheck();
        
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.addNewOrder (klantId, sqlDatum, accountId, artikelId, aantal);
        }
    }
    
    public void modifyOrderByKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        bv.startModifyOrder ();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine();
        int keuze = inputIntCheck(intKeuze);
        
        switch(keuze){
            case 1:
                addArticleToOrderByKlant(klantId);
                modifyOrderByKlant ();
                break;
            case 2:
                modifyArticleFromOrderByKlant(klantId);
                modifyOrderByKlant ();
                break;
            case 3:
                return;
            default:
                modifyOrderByKlant ();
                break;
        }        
    }
    
    private void addArticleToOrderByKlant (int klantId) {
        
        bv = new BestellingView ();
        ac = new ArtikelController ();
        av = new ArtikelView ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModifyCheck(klantId);
        
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.createArticleToAddToOrder(bestelId, artikelId, aantal);
        }
    }
    
    private void modifyArticleFromOrderByKlant (int klantId) {
        bac = new BestelArtikelController ();
        ac = new ArtikelController ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModifyCheck(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        int regelId = bac.inputIdInDatabaseCheck();
        
        av.showInputArticleIdToAddToOrder();
        int modifiedArtikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
        }
    }
    
    public void deleteOrderByKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        
        bv.startDeleteOrder();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze = inputIntCheck(intKeuze);
        
        switch (keuze) {
            case 1:
                deleteOAFromOrderByKlant (klantId);
                deleteOrderByKlant ();
                break;
            case 2:
                deleteTotalOrderByKlant (klantId);
                deleteOrderByKlant ();
                break;
            case 3:
                return;
            default:
                deleteOrderByKlant ();
                break;           
        }
    }
    
    private void deleteOAFromOrderByKlant (int klantId) {
        bv = new BestellingView ();
        bac = new BestelArtikelController ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToDeleteCheck (klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        int brId = bac.inputIdInDatabaseCheck();
        
        if (deleteConfirmed()) {
            bm.deleteOA (klantId, brId, bestelId);
        }
    }

    private void deleteTotalOrderByKlant (int klantId) {
        bv = new BestellingView ();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToDeleteCheck (klantId);
        
        if (deleteConfirmed()) {
            bm.deleteOrder (klantId, bestelId);
        }
    }
    
    //Medewerker-opties    
    public void startNewOrder () {
        
        bv = new BestellingView ();
        
        int accountId = lm.getAccountPojo().getIdAccount();
        bv.showNewBestelling();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze  = inputIntCheck(intKeuze);
        
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
        av = new ArtikelView ();
         
        int klantId = klantLijst();
        //int klantId = inputKlantIdCheck();
        
        Date sqlDatum = inputDateCheck();
        
        av.showAllArticles();
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
        }
    }
    
    public void modifyOrder () {
        
        bv = new BestellingView ();
        
        bv.startModifyOrder ();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine();
        int keuze = inputIntCheck(intKeuze);
        
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
        av = new ArtikelView ();
        bav = new BestelArtikelView ();
        bac = new BestelArtikelController ();
        
        int klantId = klantLijst();

        //int klantId = inputKlantIdCheck();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToModifyCheck(klantId);
        
        //bv.showAllBestelRegelsByBestelId(bestelId);
        
        av.showAllArticles();
        av.showInputArticleIdToAddToOrder();        
        int artikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.createArticleToAddToOrder(bestelId, artikelId, aantal);
        }
    }
    
    private void modifyArticleFromOrder () {
        
        bv = new BestellingView ();
        ac = new ArtikelController ();
        bac = new BestelArtikelController ();
        bav = new BestelArtikelView ();
        av = new ArtikelView ();
        
        int klantId = klantLijst();
        //int klantId = inputKlantIdCheck();
        
        bv.showAllOrdersByKlantId(klantId);
        
        int bestelId = inputOrderIdToModifyCheck(klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        
        bav.showInputOAIdToModify();
        int regelId = bac.inputIdInDatabaseCheck();
        
        av.showAllArticles();
        av.showInputArticleIdToModify();
        int modifiedArtikelId = ac.inputIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        if (aantal >0) {
            bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
        }
    }
    
    public void deleteOrderOptions () {
        
        bv = new BestellingView ();
        bv.startDeleteOrder();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze = inputIntCheck(intKeuze);
        
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
        bav = new BestelArtikelView ();
        bac = new BestelArtikelController ();
        
        int klantId = klantLijst();

        //int klantId = inputKlantIdCheck();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToDeleteCheck (klantId);
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        bav.showInputOAIdToDelete();        
        int brId = bac.inputIdInDatabaseCheck();
        
        if (deleteConfirmed ()) {
            bm.deleteOA(klantId, brId, bestelId); 
        }
    }

    private void deleteTotalOrder() {
        
        int klantId = klantLijst();

        //int klantId = inputKlantIdCheck();
        
        bv.showAllOrdersByKlantId(klantId);
        int bestelId = inputOrderIdToDeleteCheck (klantId);
        
        if (deleteConfirmed()) {
            bm.deleteOrder(klantId, bestelId);
        }
    }
    
    private boolean deleteConfirmed () {
        bv = new BestellingView ();
        av = new ArtikelView ();
        bv.showAskSureToDelete();
        
        return input.nextLine().equalsIgnoreCase("J");
    }
    
    
    //private int inputKlantIdCheck () {
        
     //   bv = new BestellingView ();
     //   bv.showInputKlantId();
     //   int klantId = inputIntCheck (input.nextLine());
     //   return klantId;
    //}
    
    //methods to check input for validity
    private Date inputDateCheck () {
        
        bv = new BestellingView ();
        bv.showInputDate();
        
        String dateInput = input.nextLine();
        if (Validator.isValidDate(dateInput)) {
            LocalDate bDatum = LocalDate.parse(dateInput, format);
            return java.sql.Date.valueOf(bDatum);
        } else {
            bv.showGiveDate ();
            return inputDateCheck ();
        }
    }

    public int inputNumberToOrderCheck () {
        
        bv = new BestellingView ();
        bv.showInputNumberToOrder();
        int aantal = inputIntCheck(input.nextLine());
        return aantal;
    }
    
    public int inputOrderIdToModifyCheck (int klantId) {
        
        bv = new BestellingView ();
        bv.showOrderIdToModify();
        int bestelId = inputIdInDatabaseCheck (klantId);
        return bestelId;
    }
    
    private int inputOrderIdToDeleteCheck (int klantId) {
        
        bv = new BestellingView ();
        bv.showOrderIdToDelete ();
        int bestelId = inputIdInDatabaseCheck (klantId);
        return bestelId;
    }
    
    private int inputIntCheck (String string) {
        
        bv = new BestellingView ();
        if (Validator.isValidInt(string)) {
            return Integer.parseInt(string);
        } else {
            bv.showGiveNumber ();
            return inputIntCheck (string);
        }
    }
    
    public int inputIdInDatabaseCheck (int klantId) {
        bv = new BestellingView ();
        bm = new BestellingModel ();
        String bId = input.nextLine();
        int id = inputIntCheck (bId);
        if (bm.checkOrderId(id)) {
            return id;
        } else {
            bv.showGiveNumber();
            return inputIdInDatabaseCheck (klantId);
        }
    }
    
    //show list of clients and check klantidinput
    public int klantLijst () {
        KlantenController kc = new KlantenController(new KlantenModel(), new KlantenView());
        KlantModel klant = kc.selectKlant();
        if(klant == null)
            return 0; // afhandelen geen klant geselecteerd.
        int id = klant.getKlantPojo().getId();
        return id;
    }
}