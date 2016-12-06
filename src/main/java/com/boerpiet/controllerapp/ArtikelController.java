/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.LoginManager;
import com.boerpiet.utility.Validator;
import com.boerpiet.viewapp.ArtikelView;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author Peaq
 */
public class ArtikelController {
    private final Scanner input = new Scanner (System.in);
    private ArtikelModel am;
    private ArtikelView av;
    private final LoginManager lm;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    public ArtikelController (ArtikelModel am, LoginManager lm) {
        this.am = am;
        this.lm = lm;
        }

    public void createArticle () {
        av = new ArtikelView ();
        
        av.startCreateArticle ();
        av.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze  = inputIntCheck(intKeuze);
        
        switch (keuze) {
            case 1: addArticleToDatabase ();
                    createArticle ();
                break;
            case 2:
                return;
            default: addArticleToDatabase ();
                break;
        }        
    }
    
    private void addArticleToDatabase () {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        String naam = inputNameCheck();
        
        double prijs = inputPrijsCheck();
        
        int voorraad = inputVoorraadCheck();
        
        if (prijs >0 && voorraad >0) {
            am.addArticle(naam, prijs, voorraad);
            logger.info (" Artikel toegevoegd door "+ lm.getAccountPojo().getGebruikersnaam()
                +" "+lm.getAccountPojo().getIdAccount());
        } else {
            av.showGiveNumber();
            addArticleToDatabase ();
        }
    }
    
    public void modifyArticle () {
        av = new ArtikelView ();
        
        av.articleModifyOptions();
        av.showMenuKeuze();
        String intKeuze = input.nextLine ();
        int keuze  = inputIntCheck(intKeuze);
        
        switch (keuze) {
            case 1: modifyArticleNaam ();
                    modifyArticle ();
                break;
            case 2: modifyArticlePrijs ();
                    modifyArticle ();
                break;
            case 3: modifyArticleVoorraad ();
                    modifyArticle ();
                break;
            case 4: modifyArticleNPV ();
                    modifyArticle ();
                break;
            case 5:
                return;
            default: modifyArticle ();
                break;
        }
    }
    
    private void modifyArticleNaam () {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        av.showAllArticles();
        av.showInputArticleIdToModify();
        int id = inputArtikelIdInDatabaseCheck ();
        
        String naam = inputNameCheck();
        
        am.modifyNaam(id, naam);
        logger.info (" Naam artikel "+id+"gewijzigd door " + lm.getAccountPojo().getGebruikersnaam()
            +" "+ lm.getAccountPojo().getIdAccount());
    }
    
    private void modifyArticlePrijs () {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        av.showAllArticles();
        av.showInputArticleIdToModify();
        int id = inputArtikelIdInDatabaseCheck ();
        
        double prijs = inputPrijsCheck();
        
        if (prijs >0) {
            am.modifyPrijs(id, prijs);
            logger.info (" Prijs artikel "+id+"gewijzigd door " + lm.getAccountPojo().getGebruikersnaam()
                +" "+ lm.getAccountPojo().getIdAccount());
        } else {
            av.showErrorMessage();
            modifyArticlePrijs();
        }
    }
    
    private void modifyArticleVoorraad () {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        av.showAllArticles();
        av.showInputArticleIdToModify();
        int id = inputArtikelIdInDatabaseCheck ();
        
        int voorraad = inputVoorraadCheck();
        
        if (voorraad > 0) {
            am.modifyVoorraad(id, voorraad);
            logger.info (" Voorraad artikel "+id+"gewijzigd door " + lm.getAccountPojo().getGebruikersnaam()
                    +" "+ lm.getAccountPojo().getIdAccount());
        } else {
            av.showErrorMessage();
            modifyArticleVoorraad ();
        }
    }
    
    private void modifyArticleNPV() {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        av.showAllArticles();
        av.showInputArticleIdToModify();
        int id = inputArtikelIdInDatabaseCheck ();
        
        String naam = inputNameCheck ();
        double prijs = inputPrijsCheck ();
        int voorraad = inputVoorraadCheck ();
        
        
        if (prijs >0 && voorraad>0) {
            am.modifyNPV (id, naam, prijs, voorraad);
            logger.info (" Naam/prijs/voorraad artikel "+id+"gewijzigd door "
                    + lm.getAccountPojo().getGebruikersnaam()+" "+ lm.getAccountPojo().getIdAccount());
        } else {
            av.showErrorMessage();
            modifyArticleNPV ();
        }
    }
    
    public void deleteArticleMenu () {
        av = new ArtikelView ();
        
        av.startDeleteArticle();
        av.showMenuKeuze();
        
        String intKeuze = input.nextLine ();
        int keuze = inputIntCheck (intKeuze);
        
        switch (keuze) {
            case 1: deleteArticleFromDatabase ();
                    deleteArticleMenu ();
                break;
            case 2:
                return;
            default: deleteArticleMenu ();
                break;
        }
    }
    
    private void deleteArticleFromDatabase () {
        av = new ArtikelView ();
        am = new ArtikelModel ();
        
        av.showAllArticles();
        av.showInputArticleIdToDelete();
        
        int id = inputArtikelIdInDatabaseCheck ();
        
        if (deleteConfirmed() ) {
        am.deleteArticle(id);
        logger.info (" Artikel "+id+"verwijderd door " + lm.getAccountPojo().getGebruikersnaam()
                +" "+lm.getAccountPojo().getIdAccount());
        }
    }
    
    private boolean deleteConfirmed () {
        av = new ArtikelView ();
        av.showAskSureToDelete();
        
        return input.nextLine().equalsIgnoreCase("J");
    }
    
    //methods to check input for validity   
    private String inputNameCheck () {
        av = new ArtikelView ();
        av.showInputName();
        
        String naam = notEmptyNameInput ();
        return naam;
    }
    
    private double inputPrijsCheck () {
        av = new ArtikelView ();
        av.showInputPrijs();
        
        double prijs = inputDoubleCheck (input.nextLine());
        return prijs;
    }
    
    private int inputVoorraadCheck () {
        av = new ArtikelView ();
        av.showInputVoorraad();

        int voorraad = inputIntCheck(input.nextLine());
        return voorraad;
    }
    
    public int inputArtikelIdInDatabaseCheck () {
        av = new ArtikelView();
        am = new ArtikelModel ();
        
        String aId = input.nextLine();
        int id = inputIntCheck(aId);
        
        if (am.checkArticleIdInDatabase(id)) {
            return id;
        } else {
            av.showInputArticleId();
            return inputArtikelIdInDatabaseCheck();
        }
    }

    private int inputIntCheck (String string) {
        av = new ArtikelView ();
        if (Validator.isValidInt(string)) {
            return Integer.parseInt(string);
        } else {
            av.showGiveNumber();
            return inputIntCheck (string);
        }
    }
    
    private double inputDoubleCheck (String string) {
        av = new ArtikelView ();
        
        if (Validator.isValidDouble(string) && Validator.doubleHasMaxTwoDecimals(string)) {
            return Double.parseDouble(string);
        } else {
            av.showGivePrijs ();
            return inputDoubleCheck (string);
        }
    }
    
    private String notEmptyNameInput () {
        String text = input.nextLine();
        if(text.isEmpty()) {
            return inputNameCheck ();
        } else {
        return text;
        }
    }
}