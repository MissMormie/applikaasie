/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boerpiet.controllerapp;

import com.boerpiet.domeinapp.ArtikelModel;
import com.boerpiet.domeinapp.ArtikelPojo;
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
    private ArtikelPojo ap;
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
        int keuze  = inputIntCheck(input.nextLine());
        
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
                
        String naam = inputName();
        
        double prijs = inputPrijs();
        
        int voorraad = inputVoorraad();
        
        am.addArticle(naam, prijs, voorraad);
    }
    
    public void modifyArticle () {
        av = new ArtikelView ();
        
        av.articleModifyOptions();
        av.showMenuKeuze();
        int keuze = inputIntCheck (input.nextLine());
        
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
            case 4:
                return;
            default: modifyArticle ();
                break;
        }
    }
    
    private void modifyArticleNaam () {
        
        int id = inputIntPositiveAndInDatabaseCheck ();
        
        String naam = inputName();
        
        am.modifyNaam(id, naam);
    }
    
    private void modifyArticlePrijs () {
        
        int id = inputIntPositiveAndInDatabaseCheck ();

        double prijs = inputPrijs();
        
        am.modifyPrijs(id, prijs);
    }
    
    private void modifyArticleVoorraad () {
        
        int id = inputIntPositiveAndInDatabaseCheck ();
        
        int voorraad = inputVoorraad();
        
        am.modifyVoorraad(id, voorraad);
    }
    
    public void deleteArticleMenu () {
        av = new ArtikelView ();
        
        av.startDeleteArticle();
        av.showMenuKeuze();
        
        int keuze = inputIntCheck (input.nextLine());
        
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
        int id = inputIntPositiveAndInDatabaseCheck ();
        
        am.deleteArticle(id);
    }
    
    //input methods    
    public String inputName () {
        av = new ArtikelView ();
        av.showInputName();
        String naam = notEmptyNameInput ();
        return naam;
    }
    
    public double inputPrijs () {
        av = new ArtikelView ();
        av.showInputPrijs();
        double prijs = inputDoubleCheck();
        return prijs;
    }
    
    public int inputVoorraad () {
        av = new ArtikelView ();
        av.showInputVoorraad();
        int voorraad = inputIntCheck(input.nextLine());
        return voorraad;
    }
    
    //validate input methods
    private int inputIntCheck (String string) {
        av = new ArtikelView ();
        //String intInput = input.nextLine();
        if (Validator.isValidInt(string)) {
            return Integer.parseInt(string);
        } else {
            av.showGiveNumber();
            return inputIntCheck (string);
        }
    }
    
    public int inputIntPositiveAndInDatabaseCheck () {
        av = new ArtikelView();
        av.showAllArticles();
        av.showInputArticleId();
        String aId = input.nextLine();
        int id = inputIntCheck(aId);
        if (id >0 && am.checkArticleId(id)) {
            return id;
        } else {
            return inputIntPositiveAndInDatabaseCheck();
        }
    }
    
    private double inputDoubleCheck () {
        av = new ArtikelView ();
        
        String doubleInput = input.nextLine();
        if (Validator.isValidDouble(doubleInput)) {
            return Double.parseDouble(doubleInput);
        } else {
            av.showGivePrijs ();
            return inputDoubleCheck ();
        }
    }
    
    private String notEmptyNameInput () {
        String text = input.nextLine();
        if(text.isEmpty()) {
            return inputName ();
        } else {
        return text;
        }
    }
}