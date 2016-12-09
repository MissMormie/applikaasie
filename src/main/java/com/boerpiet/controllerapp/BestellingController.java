/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.dao.bestelartikel.BestelArtikelDaoFactory;
import com.boerpiet.dao.bestelling.BestellingDaoFactory;
import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.BestelArtikelPojo;
import com.boerpiet.domeinapp.BestellingModel;
import com.boerpiet.domeinapp.BestellingPojo;
import com.boerpiet.domeinapp.KlantModel;
import com.boerpiet.domeinapp.KlantenModel;
import com.boerpiet.domeinapp.LoginManager;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.ArtikelView;
import com.boerpiet.viewapp.BestelArtikelView;
import com.boerpiet.viewapp.BestellingView;
import com.boerpiet.viewapp.KlantenView;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
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
        while (!(keuze >=1 && keuze <=3)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
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
        ac = new ArtikelController (new ArtikelModel(), lm);
        av = new ArtikelView ();
        
        Date sqlDatum = inputDateCheck();
        
        av.showAllArticles();        
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal < 1) {
            aantal = inputNumberToOrderCheck();
        }
        
        bm.addNewOrder (klantId, sqlDatum, accountId, artikelId, aantal);
        logger.info (" Bestelling ingevoerd door "+ lm.getAccountPojo().getGebruikersnaam()
                +" "+klantId);
            //dit is voor klanten met een (inlog) account
            //bestellingen voor klanten zonder account worden ingevoerd door medewerker/admin
    }
    
    public void modifyOrderByKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        bv.startModifyOrder ();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine();
        int keuze = inputIntCheck(intKeuze);
        while (!(keuze >=1 && keuze <=3)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
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
        ac = new ArtikelController (new ArtikelModel(), lm);
        av = new ArtikelView ();
        
        bv.showOrderListByKlantId (klantId);
        bv.orderListByKlantId(klantId);

        bv.showOrderIdToAddArticle();
        int bestelId = inputOrderIdInDatabaseCheck (klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId)) {
            addArticleToOrderByKlant (klantId);
        }
        
        if (!bm.checkOrderIdByKlantId(klantId, bestelId)) {
            addArticleToOrderByKlant (klantId);
        }
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        
        av.showAllArticles();
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal <1) {
            aantal = inputNumberToOrderCheck ();
        }

        bm.createArticleToAddToOrder(bestelId, artikelId, aantal);
        logger.info (" Artikelen toegevoegd aan bestelling door "
                + lm.getAccountPojo().getGebruikersnaam() + " "+ klantId);
    }
    
    private void modifyArticleFromOrderByKlant (int klantId) {
        bv = new BestellingView ();
        bav = new BestelArtikelView ();
        av = new ArtikelView ();
        bac = new BestelArtikelController ();
        ac = new ArtikelController (new ArtikelModel(), lm);
        
        bv.showOrderListByKlantId(klantId);
        bv.orderListByKlantId(klantId);
        bv.showOrderIdToModify();
        int bestelId = inputOrderIdInDatabaseCheck(klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId)) {
            modifyArticleFromOrderByKlant (klantId);
        }
        if (!bm.checkOrderIdByKlantId(klantId, bestelId)) {
            modifyArticleFromOrderByKlant (klantId);
        }
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        bav.showInputOAIdToModify();        
        int regelId = bac.inputOAIdInDatabaseCheck(bestelId);
    
        av.showAllArticles();
        av.showInputArticleIdToModifyInOrder();
        int modifiedArtikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal <1) {
            aantal = inputNumberToOrderCheck ();
        }
        
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
        logger.info (" Bestelregel gewijzigd door "
            + lm.getAccountPojo().getGebruikersnaam() +" " + klantId);
        }
    
    public void deleteOrderByKlant () {
        
        bv = new BestellingView ();
        
        int klantId = lm.getAccountPojo().getKlantId();
        
        bv.startDeleteOrder();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze = inputIntCheck(intKeuze);
        while (!(keuze >=1 && keuze <=3)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
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
        av = new ArtikelView ();
        bav = new BestelArtikelView ();
        
        bv.orderListByKlantId(klantId);
        bv.showOrderIdToDelete();
        
        int bestelId = inputOrderIdInDatabaseCheck (klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId) &&
                !bm.checkOrderIdByKlantId(klantId, bestelId)) {
            deleteOAIdFromOrderId ();
        }
        while (!checkEmptyOAIdListByOrderId(bestelId)) {
            bv.showNoOAIdByOrderId(bestelId);
            bv.showOrderIdToDelete();
            bestelId = inputOrderIdInDatabaseCheck (klantId);
        }
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        bav.showInputOAIdToDelete();
        int brId = bac.inputOAIdInDatabaseCheck(bestelId);
        
        if (deleteConfirmed()) {
            bm.deleteOA (klantId, brId, bestelId);
            logger.info (" Bestelregel " + brId + " verwijderd van bestelling door " 
                    + klantId + lm.getAccountPojo().getGebruikersnaam()
                    +" "+ lm.getAccountPojo().getIdAccount());
        }
    }

    private void deleteTotalOrderByKlant (int klantId) {
        bv = new BestellingView ();
        
        bv.showOrderListByKlantId(klantId);
        bv.orderListByKlantId(klantId);
        bv.showOrderIdToDelete();
        int bestelId = inputOrderIdInDatabaseCheck (klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId)) {
            deleteTotalOrderByKlant (klantId);
        }
        if (!bm.checkOrderIdByKlantId(klantId, bestelId)) {
            deleteTotalOrderByKlant (klantId);
        }
        
        if (deleteConfirmed()) {
            bm.deleteOrder (klantId, bestelId);
            logger.info (" Bestelling " + bestelId + " verwijderd door "
                    + lm.getAccountPojo().getGebruikersnaam()
                    +" "+ lm.getAccountPojo().getIdAccount());
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
        while (!(keuze >=1 && keuze <=2)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
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
        
        ac = new ArtikelController (new ArtikelModel(), lm);
        av = new ArtikelView ();
         
        int klantId = selectKlantFromList();
        if (klantId == 0)
            return;
        
        if (klantId ==0) {
            return;
        }
        
        Date sqlDatum = inputDateCheck();
        
        av.showAllArticles();
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal < 1) {
            aantal = inputNumberToOrderCheck();
        }
        
        bm.addNewOrder(klantId, sqlDatum, accountId, artikelId, aantal);
        logger.info (" Bestelling ingevoerd door "+ lm.getAccountPojo().getGebruikersnaam()
               +" "+ lm.getAccountPojo().getIdAccount());
    }
    
    public void modifyOrder () {
        
        bv = new BestellingView ();
        
        bv.startModifyOrder ();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine();
        int keuze = inputIntCheck(intKeuze);
        while (!(keuze >=1 && keuze <=3)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
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
    
    private ArrayList<BestellingPojo> getAllBestellingenFromKlant(int klantId) {
        ArrayList <BestellingPojo> bList = BestellingDaoFactory.getBestellingDAO("MySQL").getAllByKlantId(klantId);
        if (bList.isEmpty()) {
            return null;
        }
        return bList;
    }
    
    private void addArticleToOrder () {
        
        bv = new BestellingView ();
        ac = new ArtikelController (new ArtikelModel(), lm);
        av = new ArtikelView ();
        bav = new BestelArtikelView ();
        bac = new BestelArtikelController ();
        
        int klantId = selectKlantFromList();
        if (klantId == 0)
            return;
        
        bv.showOrderListByKlantId(klantId);
        
        ArrayList<BestellingPojo> bestellingen = getAllBestellingenFromKlant(klantId);
        if(getAllBestellingenFromKlant(klantId) == null) {
            bv.showNoBestellingByKlant();
            addArticleToOrder();
            return;
        }
        
        bv.showBestellingListByKlantId(bestellingen);
        bv.showOrderIdToAddArticle();
        
        
        int bestelId = inputOrderIdInDatabaseCheck(klantId);
        
        av.showAllArticles();
        av.showInputArticleIdToAddToOrder();
        int artikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal <1) {
            aantal = inputNumberToOrderCheck();
        }
        bm.createArticleToAddToOrder(bestelId, artikelId, aantal);
        logger.info (" Artikel " + artikelId + " toegevoegd aan bestelling door " 
                + lm.getAccountPojo().getGebruikersnaam()
                +" "+ lm.getAccountPojo().getIdAccount());
    }
    
    private void modifyArticleFromOrder () {
        
        bv = new BestellingView ();
        ac = new ArtikelController (new ArtikelModel(), lm);
        bac = new BestelArtikelController ();
        bav = new BestelArtikelView ();
        av = new ArtikelView ();
        
        int klantId = selectKlantFromList();
        if(klantId == 0) 
            return;
        
        bv.showOrderListByKlantId(klantId);
        
        ArrayList<BestellingPojo> bestellingen = getAllBestellingenFromKlant(klantId);
        if(getAllBestellingenFromKlant(klantId) == null) {
            bv.showNoBestellingByKlant();
            modifyArticleFromOrder ();
            return;
        }
        
        bv.showBestellingListByKlantId(bestellingen);
        
        bv.showOrderIdToModify();        
        int bestelId = inputOrderIdInDatabaseCheck (klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId)) {
            modifyArticleFromOrder ();
        }
        if (!bm.checkOrderIdByKlantId(klantId, bestelId)) {
            modifyArticleFromOrder ();
        }
        
        bv.showAllBestelRegelsByBestelId(bestelId);        
        bav.showInputOAIdToModify();
        int regelId = bac.inputOAIdInDatabaseCheck(bestelId);
                
        av.showAllArticles();
        av.showInputArticleIdToModifyInOrder();
        int modifiedArtikelId = ac.inputArtikelIdInDatabaseCheck();
        
        int aantal = inputNumberToOrderCheck();
        
        while (aantal <1) {
            aantal = inputNumberToOrderCheck();
        }
        bm.modifyArticleInOrder (bestelId, regelId, modifiedArtikelId, aantal);
        logger.info (" Bestelregel " + regelId + " gewijzigd in bestelling "+bestelId
                + " door "+ lm.getAccountPojo().getGebruikersnaam()
                +" "+ lm.getAccountPojo().getIdAccount());
    }
    
    public void deleteOrderOptions () {
        
        bv = new BestellingView ();
        bv.startDeleteOrder();
        bv.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze = inputIntCheck(intKeuze);
        while (!(keuze >=1 && keuze <=3)) {
            bv.showGiveNumber();
            keuze = inputIntCheck (input.nextLine());
        }
        
        switch (keuze) {
            case 1:
                deleteOAIdFromOrderId ();
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
    
    private void deleteOAIdFromOrderId () {
        
        bv = new BestellingView ();
        bav = new BestelArtikelView ();
        bac = new BestelArtikelController ();
        
        int klantId = selectKlantFromList();
        if (klantId ==0 )
            return;
        
        bv.showOrderListByKlantId(klantId);
        bv.orderListByKlantId(klantId);
        bv.showOrderIdToDelete();
        int bestelId = inputOrderIdInDatabaseCheck (klantId);//invoer negatief getal
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId) &&
                !bm.checkOrderIdByKlantId(klantId, bestelId)) {
            deleteOAIdFromOrderId ();
        }
        
        while (!checkEmptyOAIdListByOrderId(bestelId)) {
            bv.showNoOAIdByOrderId(bestelId);
            bv.showOrderIdToDelete();
            bestelId = inputOrderIdInDatabaseCheck (klantId);
        }
        
        bv.showAllBestelRegelsByBestelId(bestelId);
        bav.showInputOAIdToDelete();        
        int brId = bac.inputOAIdInDatabaseCheck(bestelId);
        
        while (!bm.checkOAIdBelongsToOrderId(bestelId, brId)) {
            bv.showNoOAIdByOrderId(bestelId);
            brId = bac.inputOAIdInDatabaseCheck(bestelId);
        }
        
        if (deleteConfirmed ()) {
            bm.deleteOA(klantId, brId, bestelId); 
            logger.info (" Bestelregel " + brId + " verwijderd van bestelling "
                    + bestelId + " door " + lm.getAccountPojo().getGebruikersnaam()
                    +" "+ lm.getAccountPojo().getIdAccount());
        } else {
            deleteOrderOptions ();
        }
    }
    
    private void deleteTotalOrder() {
        
        int klantId = selectKlantFromList();
        if (klantId == 0)
            return;
        
        if (klantId ==0) {
            return;
        }
        
        bv.showOrderListByKlantId(klantId);
        bv.orderListByKlantId(klantId);
        bv.showOrderIdToDelete();
        int bestelId = inputOrderIdInDatabaseCheck (klantId);
        
        if (!bm.checkNotEmptyOrderListByKlantId(klantId)) {
            deleteTotalOrder ();
        }
        if (!bm.checkOrderIdByKlantId(klantId, bestelId)) {
            deleteTotalOrder ();
        }

        if (deleteConfirmed()) {
            bm.deleteOrder(klantId, bestelId);
            logger.info (" Bestelling " + bestelId + " verwijderd door " 
                    + lm.getAccountPojo().getGebruikersnaam()
                    +" "+ lm.getAccountPojo().getIdAccount());
        } else {
            deleteOrderOptions ();
        }
    }
    
    private boolean deleteConfirmed () {
        bv = new BestellingView ();
        av = new ArtikelView ();
        bv.showAskSureToDelete();

        String answer = input.nextLine();
        while (!answer.equalsIgnoreCase("J") && !answer.equalsIgnoreCase("N")) {
            bv.showAskSureToDelete();
            answer = input.nextLine();        
        }
        return answer.equalsIgnoreCase("J") ;
    }
    
    //methods to check input for validity
    private boolean checkEmptyOAIdListByOrderId (int bestelId) {
        
        bv = new BestellingView ();
        
        ArrayList <BestelArtikelPojo> baList = BestelArtikelDaoFactory.getBestelArtikelDAO("MySQL").
                getBestelLijstByBestelId(bestelId);
        if (!baList.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
    
    private Date inputDateCheck () {
        
        bv = new BestellingView ();
        bv.showGiveDate();
        
        String dateInput = input.nextLine();
        if (Validator.isValidDate(dateInput)) {
            LocalDate bDatum = LocalDate.parse(dateInput, format);
            return java.sql.Date.valueOf(bDatum);
        } else {
            return inputDateCheck ();
        }
    }

    private int inputNumberToOrderCheck () {
        
        bv = new BestellingView ();
        bv.showInputNumberToOrder();
        int aantal = inputIntCheck(input.nextLine());
        return aantal;
    }
    
    private int inputIntCheck (String string) {
        
        bv = new BestellingView ();
        if (Validator.isValidInt(string)) {
            return Integer.parseInt(string);
        } else {
            bv.showGiveNumber ();
            return inputIntCheck (input.nextLine());
        }
    }
    
    private int inputOrderIdInDatabaseCheck (int klantId) {
        bv = new BestellingView ();
        bm = new BestellingModel ();
        
        String aId = input.nextLine();
        int id = inputIntCheck(aId);
                
        if (bm.checkOrderIdInDatabase(id, klantId)) {
            return id;
        } else {
            bv.showGiveNumber();
            return inputOrderIdInDatabaseCheck (klantId);
        }
    }
    
    //show list of clients and check klantidinput
    private int selectKlantFromList () {
        KlantenController kc = new KlantenController(new KlantenModel(), new KlantenView());
        KlantModel klant = kc.selectKlant();
        if(klant == null)
            return 0; // afhandelen geen klant geselecteerd.
        int id = klant.getKlantPojo().getId();
        return id;
    }
}
