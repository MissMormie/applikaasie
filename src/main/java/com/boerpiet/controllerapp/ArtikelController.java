/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.cheeseapp.Artikel.ArtikelDaoFactory;
import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.Validator;
import com.boerpiet.viewapp.ArtikelView;
import java.util.Scanner;

/**
 *
 * @author Peaq
 */
public class ArtikelController {
    private final Scanner input = new Scanner (System.in);
    private ArtikelModel am;
    private ArtikelView av;
    
    public ArtikelController (ArtikelModel am) {
        this.am = am;
        }

    public ArtikelController() {
        
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
            System.out.println(voorraad);
            am.modifyVoorraad(id, voorraad);
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
        
        if (Validator.isValidDouble(string)) {
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